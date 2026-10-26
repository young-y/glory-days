/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.foundation.data.model.domain.page;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glory.foundation.basic.domain.GloryResponse;

import java.util.Optional;

/**
 * @author : YY
 * @date : 2025/11/18
 * @descprition :
 *
 */

public class PageResponse<E> extends GloryResponse<E> {

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@JsonIgnore
	public void initPage(Page page){
		Optional.ofNullable(page).ifPresent(p->{
			if (null == this.page){
				this.page = new Page();
			}
			this.page.initPageBase(p);
		});
	}

}
