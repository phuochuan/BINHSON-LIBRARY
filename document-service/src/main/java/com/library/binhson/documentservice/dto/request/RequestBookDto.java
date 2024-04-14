package com.library.binhson.documentservice.dto.request;

import com.library.binhson.documentservice.entity.*;

import java.sql.Date;
import java.util.Set;

public record RequestBookDto(String name,
                             Set<Long> author_ids,
                             Integer year_of_publish,
                             Integer republish_time,
                             Set<Long> category_ids,
                             Integer length,
                             QualityPaper quality,
                             Long stogre_invoince_id,
                             String book_style,
                             Long local_address_id,
                             Float thickness,
                             Float height,
                             Float weight,
                             //100-0 %.
                             Float depreciation,
                             Float size,
                             String fileName
) {
}
