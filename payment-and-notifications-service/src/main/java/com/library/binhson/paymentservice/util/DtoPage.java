package com.library.binhson.paymentservice.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DtoPage {

    private List<Object> dataList;

    public DtoPage(List<Object> dataList) {
        this.dataList = dataList;
    }
    public List<Object> getPage(int limit, int offset ){
        if(limit<=0 || offset<=0) return new ArrayList<>();
        assert dataList!=null && !dataList.isEmpty();
        int start=limit*(offset-1)-1;
        if(start>dataList.size()) return new ArrayList<>();
        int end=limit*offset-1;
        end= Math.min(end, dataList.size() - 1);
        List<Object> dataOnPage=new ArrayList<>();
        for(int i=start; i<=end; i++){
            dataOnPage.add(dataList.get(i));
        }
        return dataOnPage;
    }
}

