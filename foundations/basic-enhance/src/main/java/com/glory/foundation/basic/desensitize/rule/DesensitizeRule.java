/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.basic.desensitize.rule;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.basic.crypto.CryptoContext;
import com.glory.foundation.basic.crypto.CryptoType;
import jakarta.annotation.Nonnull;
import org.springframework.util.CollectionUtils;


import java.util.List;

/**
 * @author : YY
 * @date : 2025/12/23
 * @descprition :
 *	add config at application.yml
 *	eg:
 *	glory:
 *   desensitize:
 *     rules[0]:
 *       pattern: ^(.{2}).*$
 *       replacement: $1*****
 *       names: ^\w*m\w+$
 *       excludes: case
 *       enabled: true
 *  service restriction:
 *  1. It is recommended for use in log desensitization
 *  2. String type property
 *  3. It is globe scope
 *  when special specification is required, place use @Desensitize
 */
public class DesensitizeRule {
	private String algorithm = CryptoType.MASK.value();//default
	private String pattern; //mask type,format template,regex
	private String replacement;//mask type, replace mode
	private int minLength = 4;//mask type, min length,if text length less than minLength,then return minLengthStr
	private String minLengthStr;//mask type
	private boolean withPrefix = false;//whether add prefix at first,default false
	private boolean enabled = true;//whether rule enabled
	private String prefix;//if withPrefix is true,then prefix is first
	private int priority = 5;//rule priority,default 5,smaller is higher priority
	private List<String> names;//match property name
	private List<String> excludes;//exclude property name,not output

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public String getMinLengthStr() {
		return minLengthStr;
	}

	public void setMinLengthStr(String minLengthStr) {
		this.minLengthStr = minLengthStr;
	}

	public boolean isWithPrefix() {
		return withPrefix;
	}

	public void setWithPrefix(boolean withPrefix) {
		this.withPrefix = withPrefix;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<String> getExcludes() {
		return excludes;
	}

	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}

	public boolean effective(){
		return enabled && (!CollectionUtils.isEmpty(excludes) || !CollectionUtils.isEmpty(names));
	}
	@JsonIgnore
	public boolean include(@Nonnull String name){
		return null == excludes || !excludes.contains(name);
	}

	/**
	 * whether match the rule by name
	 * @param name
	 * @return
	 */
	@JsonIgnore
	public boolean match(@Nonnull String name){
		return null != names && names.stream().anyMatch(name::matches);
	}

	@JsonIgnore
	public CryptoContext generate(){
		CryptoContext context = new CryptoContext();
		CryptoType type = CryptoType.fromPersistableValue(algorithm);
		if (null == type){
			type = CryptoType.MASK;
		}
		context.setType(type);
		context.setPattern(pattern);
		context.setReplacement(replacement);
		context.setMinLength(minLength);
		context.setMinLengthStr(minLengthStr);
		context.setWithPrefix(withPrefix);
		context.setPrefix(prefix);
		return context;
	}
}
