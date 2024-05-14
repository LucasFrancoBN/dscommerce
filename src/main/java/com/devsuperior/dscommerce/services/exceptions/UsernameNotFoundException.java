package com.devsuperior.dscommerce.services.exceptions;

public class UsernameNotFoundException extends RuntimeException{
  public UsernameNotFoundException(String msg){
    super(msg);
  }
}
