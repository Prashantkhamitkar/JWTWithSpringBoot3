package com.hotelbooking.dto;


import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class ResponseDto {
private String token;
private String email;
public ResponseDto(String token,String email) {
	this.token=token;
	this.email=email;
}

}
