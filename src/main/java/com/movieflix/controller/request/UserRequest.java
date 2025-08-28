package com.movieflix.controller.request;


import lombok.Builder;

@Builder
public record UserRequest(Long id, String name, String email, String password) {



}
