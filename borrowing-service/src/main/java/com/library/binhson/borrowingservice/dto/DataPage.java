package com.library.binhson.borrowingservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class DataPage {
    private int foundValueCount;
    private int limit;
    private int offset;
    private List<Object> data;

    public DataPage(int limit, int offset, List<Object> objects) {
        this.foundValueCount = objects.size();
        if(limit<=0) this.limit=100;
        else  this.limit = limit;
        if(offset<=0) this.offset=1;
        else this.offset = offset;
        this.data=objects;

    }

    public List<Object> getData(){
        List<Object> result=new ArrayList<>();
        if(Objects.nonNull(this.data) && this.data.size()>0) {
            for (int i = limit * (offset - 1); i < limit * offset; i++) {
                if (i == this.data.size()) break;
                result.add(this.data.get(i));
            }
        }
        return  result;

    }
}
