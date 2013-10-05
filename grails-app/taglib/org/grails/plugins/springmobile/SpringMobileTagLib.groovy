package org.grails.plugins.springmobile

class SpringMobileTagLib {

	static namespace = "sm"
	
	def ifMobileDevice = { attrs, body ->
		def device = request.getAttribute("currentDevice")
		if(device && device.isMobile()){
			out << body()
		}
	}
	def ifNotMobileDevice = { attrs, body ->
		def device = request.getAttribute("currentDevice")
		if(!device || !device.isMobile()){
			out << body()
		}
	}
	
	// Auto-adapt mobile tags
	def actionSubmit = {attrs, body ->
		if(isMobileDevice()) {
			
		} else {
			out << g.actionSubmit(attrs,body)
		}
	}

	
}
