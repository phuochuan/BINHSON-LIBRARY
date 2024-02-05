package com.library.binhson.userservice.dto;

import com.library.binhson.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Data
@Slf4j
public class UserPageDto {
    private List<UserDto> users;
    private int totalPage=0;
    private int size=0;
    private int page=0;

    public UserPageDto(List<UserDto> users, int size, int page) {
        if(Objects.isNull(users)) {
            log.info("User list is null.");
            return;
        }
        this.users = users;
        this.size = size;
        this.page = page;
        if(users.size()%size==0)
            this.totalPage = (users.size()/size);
        else  this.totalPage = (users.size()/size)+1;

    }

    public Set<?> getSet(){
        if(totalPage<=0)
            return new HashSet<>();
        else{
            Set<Object> userSet=new HashSet<>();
            int startNumber=this.size*(this.page-1);
            int endNumber=this.size*(this.page);
            if(endNumber>this.users.size())
                endNumber=this.users.size();
            for (int i=startNumber; i<endNumber; i++) {
                userSet.add(this.users.get(i));
            }
            return userSet;
        }
    }

}
