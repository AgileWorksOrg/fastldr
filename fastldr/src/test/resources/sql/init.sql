drop table TEST_TABLE if exists;
create table TEST_TABLE (
  id integer,
  name varchar(50),
  url varchar(100),
);
insert into TEST_TABLE (id, name, url) values (10, 'Groovy', 'http://groovy.codehaus.org');
insert into TEST_TABLE (id, name, url) values (20, 'AgileWorks', 'http://www.agileworks.org');