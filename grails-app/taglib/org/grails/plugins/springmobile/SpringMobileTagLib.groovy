package org.grails.plugins.springmobile

class SpringMobileTagLib {

	static namespace = "sm"
	
	def ifMobileDevice = { attrs, body ->
		def device = request.getAttribute("currentDevice")
		if(device && device.isMobile()){
			pageScope.doElseNotMobile=false
			out << body()
		} else {
			pageScope.ifMobileElse=true
		}
	}
	
	def elseNotMobile = { attrs, body ->
		if(pageScope.doElseNotMobile)  out << body()
	} 
	
	def ifNotMobileDevice = { attrs, body ->
		def device = request.getAttribute("currentDevice")
		if(!device || !device.isMobile()){
			out << body()
		}
	}
	
}
