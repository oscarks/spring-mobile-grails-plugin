package org.grails.plugins.springmobile

class SpringMobileTagLib {
	def ifMobileDevice = { attrs, body ->
		def device = request.getAttribute("currentDevice")
		if(device.isMobile()){
			out << body()
		}
	}
	def ifNotMobileDevice = { attrs, body ->
		def device = request.getAttribute("currentDevice")
		if(!device.isMobile()){
			out << body()
		}
	}
}
