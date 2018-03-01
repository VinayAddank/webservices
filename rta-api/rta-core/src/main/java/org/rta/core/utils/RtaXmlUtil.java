package org.rta.core.utils;

import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.rta.core.model.common.ModelList;

public class RtaXmlUtil {
    private static final Logger log = Logger.getLogger(RtaXmlUtil.class);

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String marshal(Class<?> modelClass, List<?> list, String name) {
        try {
            StringWriter stringWriter = new StringWriter();
            JAXBContext jc = JAXBContext.newInstance(ModelList.class, modelClass);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            QName qName = new QName(name);
            ModelList wrapper = new ModelList(list);
            JAXBElement<ModelList> jaxbElement = new JAXBElement<ModelList>(qName, ModelList.class, wrapper);
            m.marshal(jaxbElement, stringWriter);

            return stringWriter.toString();
        } catch (JAXBException e) {
            log.error("Exception while marshalling:" + e.getMessage());
            log.debug(e);
        }
        return null;
    }

    public static boolean xmlResponseFormat(HttpServletRequest request) {
        if (request.getRequestURI().endsWith(".xml") || "xml".equalsIgnoreCase(request.getParameter("format")))
            return true;
        return false;
    }
}
