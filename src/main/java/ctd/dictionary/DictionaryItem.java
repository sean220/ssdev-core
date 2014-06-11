package ctd.dictionary;

import java.io.Serializable;
import java.util.HashMap;

import ctd.util.converter.ConversionUtils;

public class DictionaryItem implements Serializable{
	private static final long serialVersionUID = -2624948204291546508L;
	private String key;
	private String text;
	private String mCode;
	private boolean leaf;
	private String parent;
	private int index;
	private HashMap<String,Object> properties;
	
	public DictionaryItem(){
		
	}
	
	public DictionaryItem(String key,String text){
		this.setKey(key);
		this.setText(text);
	}
	
	public void setProperty(String nm,Object v){
		if(properties == null){
			properties = new HashMap<String,Object>();
		}
		properties.put(nm, v);
	}
	
	public boolean hasProperty(String nm){
		if(properties == null){
			return false;
		}
		return properties.containsKey(nm);
	}
	
	public Object getProperty(String nm){
		if(properties == null){
			return null;
		}
		return properties.get(nm);
	}
	
	public <T> T getProperty(String nm,Class<T> t){
		if(properties == null){
			return null;
		}
		return ConversionUtils.convert(properties.get(nm),t);
	}
	
	public HashMap<String,Object> getProperties(){
		if(properties != null && properties.isEmpty()){
			return null;
		}
		return properties;
	}
	
	public void setProperties( HashMap<String,Object> properties){
		this.properties = properties;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMCode() {
		return mCode;
	}

	public void setMCode(String mCode) {
		this.mCode = mCode;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


}
