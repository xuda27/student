drop table classes;
drop table student;
drop sequence seq_cid
drop sequence seq_sid
--班级信息

create table classes(
	cid number(10) primary key,
	cname varchar2(100)
);

create or replace trigger tri_classes 
before insert on CLASSES
for each row
begin
	select seq_cid.nextval into :new.cid from dual;
end;

--学生信息表
create table student(
	sid number(10) primary key,
	cid number(10)
		constraint FK_student_cid references classes(cid),
		sname varchar2(100),
		age number(3),
		tel varchar2(15),
		photo varchar2(500) --实际存的是，图片在服务器中的相对路径
		
);
create or replace trigger tri_student 
before insert on student
for each row
begin
	select seq_sid.nextval into :new.sid from dual;
end;
create sequence seq_sid start with 1001;

select * from CLASSES
