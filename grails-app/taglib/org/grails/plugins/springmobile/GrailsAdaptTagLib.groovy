package org.grails.plugins.springmobile

class GrailsAdaptTagLib {
	static namespace = "g"

	// Auto-adapt mobile tags
	def actionSubmit = {attrs, body ->
		if(mobileDevice) {
			def pars=""
			if (attrs.action) pars+="name=\"_action_${attrs.action}\" id=\"_action_${attrs.action}\" "
			pars+=attrs.findAll {k,v-> !(k in ['action','value'] ) }.collect {k,v-> "$k=\"$v\""}.join(" ")
			out << "<button type=\"submit\" $pars data_ajax=\"false\" >${attrs.value}</button>"
		} else {
			def applicationTagLib = grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
			applicationTagLib.link.actionSubmit(attrs, body)
		}
	}

	def datePicker = {attrs, body ->
		
		if(mobileDevice) {
			def pars=""
			pars+=attrs.findAll {k,v-> !(k in ['type'] ) }.collect {k,v-> "$k=\"$v\""}.join(" ")
			out << """<input type="date" $pars />"""
		} else if (grailsApplication.config.springmobile.taglib.datepicker.type=='jquery-ui') {
			def name = attrs.name    //The name attribute is required for the tag to work seamlessly with grails
			def id = attrs.id ?: name
			def minDate = attrs.minDate
			def showDay = attrs.showDay
			def  value = attrs.value
			def dateFormat = attrs.dateFormat ?: attrs.format
			def jsDateFormat
			if (dateFormat)
				jsDateFormat=dateFormat.replaceAll('M', 'm').replaceAll('yy', 'y')
			def svalue
			if (value) 
				svalue=value.format(dateFormat ?: 'yyyy/MM/dd', value)
				
			out <<  "<input type=\"text\" name=\"${name}\" id=\"${id}\" ${svalue ? 'value=\"'+svalue+'\"' : ''} />"
			out <<  "<input type=\"hidden\" name=\"${name}_day\" id=\"${id}_day\" />"
			out <<  "<input type=\"hidden\" name=\"${name}_month\" id=\"${id}_month\" />"
			out <<  "<input type=\"hidden\" name=\"${name}_year\" id=\"${id}_year\" />"
	
			out <<  "<script type=\"text/javascript\"> "
			out <<  "\$(function(){"
			out <<  "\$(\"#${id}\").datepicker({"
			out <<  "onClose: function(dateText, inst) {"
			if (jsDateFormat) {
				out <<  "var myDate=\$.datepicker.parseDate('${jsDateFormat}',dateText);"
				out <<  "\$(\"#${id}_month\").attr(\"value\",myDate.getMonth() +1);"
				out <<  "\$(\"#${id}_day\").attr(\"value\",myDate.getDate());"
				out <<  "\$(\"#${id}_year\").attr(\"value\",myDate.getFullYear());"

			} else {
				out <<  "\$(\"#${id}_month\").attr(\"value\",new Date(dateText).getMonth() +1);"
				out <<  "\$(\"#${id}_day\").attr(\"value\",new Date(dateText).getDate());"
				out <<  "\$(\"#${id}_year\").attr(\"value\",new Date(dateText).getFullYear());"
			}
			out <<  "}"
			if(jsDateFormat) {
				out << ","
				out << "dateFormat: '${jsDateFormat}'"
			}
			if(minDate != null){
				out <<  ","
				out <<  "minDate: ${minDate}"
			}
	
			if(showDay != null){
				out <<  ","
				out <<  "beforeShowDay: function(date){"
				out << 	"var day = date.getDay();"
				out << 	"return [day == ${showDay},\"\"];"
				out <<  "}"
			}
	
			out <<  "});"
			def mask = (dateFormat  ?: 'yyyy/MM/dd').replaceAll('[dmyMhs]','9')
			out << "if(\$.mask) { \$(\"#${id}\").mask(\"${mask}\");};"
			out <<  "})</script>"
		} else {
			def applicationTagLib = grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
			applicationTagLib.datepicker.actionSubmit(attrs, body)
		}
	}

	

