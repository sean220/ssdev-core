package ctd.controller.support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import ctd.controller.Configurable;
import ctd.controller.ConfigurableLoader;
import ctd.controller.exception.ControllerException;
import ctd.resource.ResourceCenter;
import ctd.schema.constants.DataTypes;
import ctd.util.xml.XMLHelper;

public abstract class AbstractConfigurableLoader<T extends Configurable> implements ConfigurableLoader<T> {
	protected String postfix = ".xml";
	
	protected String getPathFromId(String id) throws ControllerException{
		return id.replaceAll("\\.", "/") + postfix;
	}
	
	@Override
	public T load(String id) throws ControllerException {
		String path = getPathFromId(id);
		return loadFromPath(id,path);
	}
	
	protected T loadFromPath(String id,String path) throws ControllerException {
		try {
			Resource r = ResourceCenter.load(ResourceUtils.CLASSPATH_URL_PREFIX,path);
			Document doc = XMLHelper.getDocument(ResourceCenter.getInputStream(r));
			doc.getRootElement().addAttribute("path", r.getFile().getAbsolutePath());
			return createInstanceFormDoc(id,doc,r.lastModified());
		} 
		catch (FileNotFoundException e) {
			throw new ControllerException(e,ControllerException.INSTANCE_NOT_FOUND,"file[" + path + "] not found.");
		}
		catch (IOException e) {
			throw new ControllerException(e,ControllerException.IO_ERROR,"load file[" + path + "] io error.");
		}
		catch(DocumentException e){
			throw new ControllerException(e,ControllerException.PARSE_ERROR,"load file[" + path + "] parse document error.");
		}
		catch (Exception e) {
			throw new ControllerException(e,"load file[" + path + "] unknow error.");
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void setupProperties(Configurable o,Element el){
		List<Element> ls = el.selectNodes("properties/p");
		for(Element p : ls){
			String type = p.attributeValue("type","string");
			Object v = DataTypes.toTypeValue(type, p.getTextTrim());
			o.setProperty(p.attributeValue("name"), v);
		}
	}
	
	public abstract T createInstanceFormDoc(String id,Document doc,long lastModi) throws ControllerException;

}
