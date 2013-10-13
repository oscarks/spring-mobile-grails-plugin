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
	
	// Auto-adapt mobile tags
	def actionSubmit = {attrs, body ->
		if(isMobileDevice()) {
			def action	= attrs.action
			def value	= attrs.value
			def name	= attrs.name
			out << "<input type=\"hidden\" name=\"_action_$action\" value=\"${value}\"/>"
			def pars=""
			attrs.findAll { !(it.key in ['action','value'])}.each {k,v->
				pars+=" $k=\"v\" "
			}
			if (name && !("id" in attrs.keySet()))
				pars+= " id=\"${name}\" "
			out << "<button $pars type=\"submit\">${value}</button>"
		} else {
			out << g.actionSubmit(attrs,body)
		}
	}
	
	

	
}
