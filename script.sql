select *
from student;

select*
from student
where age>15 and age<19;

select name
from student;

select *
from student
where name like '%n%';

select *
from student
where age < id;

select id, name, age
from student
order by age;