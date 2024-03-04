package com.library.binhson.documentservice.ultil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Slf4j
public class PageUtilObject {
    private int limit;
    private int pageTotal;
    private int currentPage;
    private List<Object> objects;

    public PageUtilObject(int limit, int currentPage, List<Object> objects) {
        this.limit = limit;
        this.currentPage = currentPage;
        this.objects = objects;
        if (Objects.isNull(objects)) {
            log.error("List is null");
            pageTotal = 0;
            this.objects = new ArrayList<>();
            return;
        }
        int dataSize = objects.size();
        int surplus = dataSize % limit;
        if (surplus == 0) pageTotal = dataSize / limit;
        else pageTotal = dataSize / limit + 1;
    }

    public List<?> getData() {
        if (limit == 0 || currentPage == 0 || pageTotal == 0
        ) {
            log.error("limit ==0|| currentPage ==0 || pageTotal==0");
            return new ArrayList<>();
        }
        List<Object> data = new ArrayList<>();
        int i = 0;
        int elementsPerPage = limit;
        int startIndex = elementsPerPage * currentPage;
        int endIndex = Math.min(startIndex + elementsPerPage, pageTotal * limit);

        while (i < endIndex) {
            log.info("i: " + i);
            if (i < objects.size()) {
                data.add(objects.get(i));
            }
            i++;
        }
        return data;
    }
}
