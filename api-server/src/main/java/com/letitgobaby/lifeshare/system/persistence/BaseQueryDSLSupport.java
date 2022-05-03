package com.letitgobaby.lifeshare.system.persistence;

import java.util.List;

public interface BaseQueryDSLSupport<T> {

  List<T> findAll();

}
