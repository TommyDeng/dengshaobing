--清除所有表
select 'drop table '||TABLE_NAME  ||';' from INFORMATION_SCHEMA.TABLES t where t.TABLE_CATALOG ='TEST' and t.TABLE_SCHEMA   = 'PUBLIC';
