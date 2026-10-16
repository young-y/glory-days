/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.resource.rest;

import com.glory.foundation.basic.domain.GloryResponse;
import com.glory.foundation.basic.formater.FormatHelper;
import com.glory.magic.core.domain.code.MagicCodeBo;
import com.glory.magic.core.domain.code.MagicCodeItemBo;
import com.glory.magic.resource.domain.bo.MagicQueryRequest;
import com.glory.magic.resource.domain.bo.MagicQueryResponse;
import com.glory.magic.resource.service.MagicCodeService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author YY
 * @date 2026/9/21
 * @description: TODO
 */
@RestController
@RequestMapping("/code")
public class MagicCodeRestful {
    @Autowired
    private MagicCodeService codeService;
    @PostMapping("/save")
    public GloryResponse<MagicCodeBo> saveAndUpdate(@RequestBody MagicCodeBo bo){
        return GloryResponse.Success(codeService.saveAndUpdate(bo));
    }

    @PostMapping("/delete/{listId}")
    public void deleteCode(@PathVariable("listId") Long listId){
        codeService.deleteCode(listId);
    }
    @PostMapping("/deleteByPlanId/{planId}")
    public void deleteCodeByPlanId(@PathVariable("planId") Long planId){
        codeService.deleteCodeByPlanId(planId);
    }

    @GetMapping("/find/{codeId}")
    public GloryResponse<MagicCodeBo> findCode(@PathVariable("codeId") Long codeId){
        return GloryResponse.Success(codeService.findCode(codeId));
    }

    @GetMapping("/findByName/{codeName}")
    public GloryResponse<MagicCodeBo> findCode(@PathVariable("codeName") String codeName){
        return GloryResponse.Success(codeService.findCode(codeName));
    }

    /**
     * search code
     * @param request {@link MagicQueryRequest}
     * @return {@link MagicQueryResponse}
     * @description:
     * listId: codeId
     * planId: planId
     * name:codeName,like
     * enabled:1 or 0
     * tenant: tenantCode
     */
    @PostMapping("/search")
    public MagicQueryResponse<MagicCodeBo> codeQuery(@RequestBody MagicQueryRequest request){
        return codeService.codeQuery(request);
    }

    @PostMapping("/item/save")
    public GloryResponse<MagicCodeItemBo> saveAndUpdate(@RequestBody MagicCodeItemBo bo){
        return GloryResponse.Success(codeService.saveAndUpdate(bo));
    }

    @PostMapping("/item/delete/{listId}")
    public void deleteCodeItem(@PathVariable("listId") Long listId){
        codeService.deleteCodeItem(listId);
    }

    @GetMapping("/item/find/{codeItemId}")
    public GloryResponse<MagicCodeItemBo> findCodeItem(@PathVariable("codeItemId") Long codeItemId){
        return GloryResponse.Success(codeService.findCodeItem(codeItemId));
    }

    /**
     * find codeItem
     * @param codeName codeName
     * @param srcValue srcValue
     * @return {@link GloryResponse}
     * @description: codeName, srcValue,
     */
    @GetMapping("/item/find/{codeName}/{srcValue}")
    public GloryResponse<MagicCodeItemBo> findCodeItem(@PathVariable("codeName") String codeName,
                                        @PathVariable("srcValue") String srcValue){
        return findCodeItem(codeName, srcValue, null);
    }
    @GetMapping("/item/find/{codeName}/{srcValue}/{srcValue1}")
    public GloryResponse<MagicCodeItemBo> findCodeItem(@PathVariable("codeName") String codeName,
                                        @PathVariable("srcValue") String srcValue,
                                        @PathVariable("srcValue1") String srcValue1){
        return GloryResponse.Success(codeService.findCodeItem(codeName, srcValue, srcValue1));
    }

    /**
     * search codeItem
     * @param request {@link MagicQueryRequest}
     * @return {@link MagicQueryResponse}
     * @description:
     * listId: 列表ID
     * name:codeName
     * srcValue: codeItemValue
     * srcValue1: codeItemValue1
     * enabled:1 or 0
     * tenant: tenantCode
     */
    @PostMapping("/item/search")
    public MagicQueryResponse<MagicCodeItemBo> codeItemQuery(@RequestBody MagicQueryRequest request){
        return codeService.codeItemQuery(request);
    }

    @GetMapping("/item/findByCodeId/{codeId}")
    public GloryResponse<MagicCodeItemBo> findCodeItemByCodeId(@PathVariable("codeId") Long codeId){
        List<MagicCodeItemBo> itemBos = codeService.findCodeItemByCodeId(codeId);
        return GloryResponse.Success(itemBos);
    }

    @PostMapping("/uploads")
    @Transactional
    public GloryResponse<MagicCodeBo> uploadCodes(@RequestParam("files") MultipartFile[] files){
        GloryResponse<MagicCodeBo> response = new GloryResponse<>();
        Optional.ofNullable( files).ifPresent(fs->{
            for (MultipartFile file : fs) {
                MagicCodeBo magicCodeBo = FormatHelper.fromResource(file.getResource(), MagicCodeBo.class);
                MagicCodeBo codeBo = codeService.saveAndUpdate(magicCodeBo);
                response.addElement(codeBo);
            }
        });
        return response;
    }

    @PostMapping("/uploadMul")
    @Transactional
    public GloryResponse<MagicCodeBo> uploadMulCodes(@RequestParam("files") MultipartFile[] files){
        GloryResponse<MagicCodeBo> response = new GloryResponse<>();
        Optional.ofNullable( files).ifPresent(fs->{
            for (MultipartFile file : fs) {
                List<MagicCodeBo> magicCodeBos = FormatHelper.fromResourceList(file.getResource(), MagicCodeBo.class);
                List<MagicCodeBo> list = magicCodeBos.stream().map(magicCodeBo -> codeService.saveAndUpdate(magicCodeBo)).toList();
                response.setElements(list);
            }
        });
        return response;
    }

    @PostMapping("/bindForPlan/{codeId}/{planId}")
    public GloryResponse<MagicCodeBo> bindCodeForPlan(@PathVariable("codeId") Long codeId, @PathVariable("planId") Long planId){
        MagicCodeBo magicCodeBo = codeService.bindCodeForPlan(codeId, planId);
        if (magicCodeBo == null){
            return GloryResponse.fail("bind fail code["+codeId+"] plan["+planId+"]");
        }
        return GloryResponse.Success(magicCodeBo);
    }
}
