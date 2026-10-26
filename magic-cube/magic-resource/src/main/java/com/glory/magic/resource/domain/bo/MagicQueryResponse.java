/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.domain.bo;

import com.glory.foundation.data.model.domain.page.PageResponse;

import java.util.List;


/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
public class MagicQueryResponse <E> extends PageResponse<E> {

    public static <E> MagicQueryResponse<E> of(E obj){
        MagicQueryResponse<E> response = new MagicQueryResponse<>();
        response.setElements(List.of(obj));
        return response;
    }
}
