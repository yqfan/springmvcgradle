package com.yqfan.springmvc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yqfan.springmvc.dao.GiftDao;
import com.yqfan.springmvc.dao.UserDao;
import com.yqfan.springmvc.model.Gift;
import com.yqfan.springmvc.model.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MyController {
	
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);
    
    @Autowired
    private GiftDao giftdao;
    
    @Autowired
    private UserDao userdao;
    
    public static final String GIFT_STORE_PATH = "/Users/yqfan/Documents/SpringServerStorage/cloudsvc/giftdata";

    
    public static String getGiftAbsPath(Gift gift) {
    	return GIFT_STORE_PATH + File.separator + "id"+gift.getId() + '_' + gift.getOriName();
    }
    private void save(Gift entity) {
        giftdao.insert(entity);
    }
    
    private Collection<Gift> getGiftList() {
    	Collection<Gift> list = giftdao.getAll();
    	return list;
    }
    
    private Gift getById(long id) {
    	return giftdao.findById(id);
    }

    
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/", "/welcome**", "/home**"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
	 
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		
		return model;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegisterPage(Model model) {
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registerAccount(@RequestParam("username") String username, @RequestParam("password") String password) {
		ModelAndView model = new ModelAndView();
		model.setViewName("registersuccess");
		if (userdao.findByName(username) != null) {
			model.addObject("name", username);
			model.addObject("message", "This username already exist.");
			model.addObject("redirect", "register");
			return model;
		}
		User user = new User();
		user.setUserName(username);
		user.setPassWord(password);
		user.setRole("ROLE_USER");
		userdao.insert(user);
		model.addObject("name", username);
		model.addObject("message", "You have successfully registered!");
		model.addObject("redirect", "home");
		return model;
	}
	
	@RequestMapping(value="/giftchain", method=RequestMethod.GET)
    public String getGiftList(Model model) {
		logger.info("getGiftList is called");
		model.addAttribute("gifts", getGiftList());
		Collection<Gift> tmp = getGiftList();
		for (Gift g:tmp) {
			logger.info("id="+g.getId()+",title="+g.getTitle());
		}
        return "giftschain";
    }
	
	@RequestMapping(value="/giftdetail", method=RequestMethod.GET)
	public ModelAndView getGiftDetail(@RequestParam("id") long id) {
		logger.info("getGiftDetail is called, giftid="+id);
		Gift gift = getById(id);
		ModelAndView model = new ModelAndView();
		model.addObject("title", gift.getTitle());
		model.addObject("desc", gift.getDescription());
		model.addObject("id", gift.getId());
		logger.info("dataurl="+gift.getDataUrl() + ",id="+gift.getId());
		return model;
	}
	
	@RequestMapping(value="/imagedisplay", method=RequestMethod.GET)
	public void showImage(@RequestParam("id") long id, HttpServletResponse response) throws IOException {
		logger.info("showImage is called");
		Gift gift = giftdao.findById(id);        
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    Path path = Paths.get(gift.getDataUrl());
	    logger.info("showImage url="+gift.getDataUrl());
	    byte[] bytes = Files.readAllBytes(path);
	    response.getOutputStream().write(bytes);

	    response.getOutputStream().close();
	}
	
	@RequestMapping(value="/uploadGift", method=RequestMethod.GET) 
	public String getUploadGiftView(){
		logger.info("/uploadGift GET method is called");
		return "upload";
	}
	
	@RequestMapping(value="/uploadGift", method=RequestMethod.POST)
    public ModelAndView uploadGift(@RequestParam("title") String title, @RequestParam("desc") String desc,
    		@RequestParam("file") MultipartFile file) {
		logger.info("/uploadGift POST method is called");
		Gift gift = new Gift(title, desc);
		
		// save will do setId, setDataUrl. 
		// if there is no image, still dataUrl=ROOT_PATH+id+original_name, but this file does not actually exist.
        
        ModelAndView model = new ModelAndView();
        model.addObject("title", title);
        model.setViewName("uploadsuccess");
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String oriFileName = file.getOriginalFilename();
                gift.setOriName(oriFileName);
                save(gift);
 
                // Creating the directory to store file
                File dir = new File(GIFT_STORE_PATH);
                if (!dir.exists())
                    dir.mkdirs();
                logger.info("dir="+dir.getAbsolutePath());
 
                // Create the file on server
                String giftAbsPath = getGiftAbsPath(gift);
                File serverFile = new File(giftAbsPath);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());
                
                model.addObject("message", "Upload successful!");
                return model;
            } catch (Exception e) {
                model.addObject("message", "Upload failed => " + e.getMessage());
                return model;
            }
        } else {
        	save(gift);
            model.addObject("message", "Uploaded without an image");
            return model;
        }
    }
    	
}
