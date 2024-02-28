package com.library.binhson.documentservice.dto.Request;

import java.util.List;

public record SetBookForAuthorRequest (List<String> book_ids){
}
