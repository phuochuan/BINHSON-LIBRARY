package com.library.binhson.userservice.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectPage
{
    private List<?> objects;
    private int pageIndex;
    private int totalPage;
    private int size;

    public ObjectPage(List<?> objects, int pageIndex , int size) {
        if(Objects.nonNull(objects) && objects.size()>0) {
            this.objects = objects;
            this.pageIndex = pageIndex;
            this.size = size;
            int surplus= objects.size()%size;
            totalPage=surplus>0? objects.size()/size +1 :objects.size()/size;
        }else{
            this.objects = new ArrayList<>();
            this.pageIndex = 1;
            this.size = 10;
            totalPage=0;
        }
    }

    public List<?> getCurrentPage(){
        if(totalPage==0)
            return new ArrayList<>();
        List<Object> result=new ArrayList<>();
        int start=size*(pageIndex-1);
        int max=size*(pageIndex)-1;
        for( int i=start; i<=max; i++ ){
            if(i==objects.size())
                break;
            result.add((Object)this.objects.get(i));
        }
        return result;
    }
}
