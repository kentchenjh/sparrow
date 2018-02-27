Inspired by Blade. The framework contains the embedd server and could be implemented in few steps which is suitable for micorservices. For now, the whole project is a draft which is unstable and untested. 

Indicate the base package which will be scanned 
```
Sparrow.me().addPackages("com.test")
or
Sparrow.me().addConfig("app.properties")
----------------app.properties---------------
base.package=com.test

```

Make a controller
```
@Controller
public class MainController {

	@Autowired
	MainService mainService;

	@Path("/hello")
	public ModelAndView hello(ModelAndView mav) {
		mav.setAttribute("name", "kent");
		mav.setView("hello");
		return mav;
	}

	@Path("/home")
	//return json
	@JSON
	public String homepage() {
		return "This is homepage";
	} 

}

```

Make a service
```
@Service
public class TestService {

}
```

AOP
```
//intercept all requests
@Hook
public class SayHiHook implements WebHook{

	@Override
	public boolean before(Signature signature) {
		
		System.out.println("before");
		return true;
	}

	@Override
	public boolean after(Signature signature) {
		
		System.out.println("after");
		return true;
	}
	
}

//intercept the path starts with /wtf/h
@Hook("/wtf/h*")
public class ModifyHook implements WebHook{

	@Override
	public boolean before(Signature signature) {
		
		System.out.println("modfiy hook before");
		
		return true;
	}
}

```

How to start it

```
//start without config
Sparrow.me().addPackages("com.test").start();

//start with config
Sparrow.me().addConfig("app.properties").start();
```

Visit the page
```
http://localhost:9000/hello
http://localhost:9000/home
```

Hate the verbose code parts above? Wanna build a easy website in the simplest way? Try this.
```
Sparrow.me().get("/", new RouteHandler() {
			
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write("welcome");
		} catch (IOException e) {
			e.printStackTrace();
			IOKit.closeQuietly(writer);
		}
	}
}).start();

```
then hit
```
http://localhost:9000/
```

There are some components can be replaced.
For exmaple,

```
//replace the default template engine by TestTemplateHandler
//the bean name is fixed
@Bean("templateEngine")
public class TestTemplateHandler extends AbstractTemplateEngine{

	public void render(ModelAndView mav) throws TemplateException {
		//do something
	};
	
}
``` 
