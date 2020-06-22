package com.example.blog.config

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class CommonRoutingDataSource : AbstractRoutingDataSource {

  override fun determineCurrentLookupKey() = DataSourceContextHolder.getDataSourceName()





}

class DataSourceContextHolder {

  companion object {
    private val contextHolder = ThreadLocal<String>()

    fun setDataSourceName(dataSourceName: String) = contextHolder.set(dataSourceName)

    fun getDataSourceName(): String = contextHolder.get()

    fun clearDataSourceName() = contextHolder.remove()
  }


}







