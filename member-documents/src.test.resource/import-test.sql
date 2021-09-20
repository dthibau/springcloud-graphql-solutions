
insert into MEMBER (EMAIL, PASSWORD, NOM, PRENOM, AGE) values ('dthibau@wmmod.com', 'secret','THIBAU','David',49);
insert into MEMBER (EMAIL, PASSWORD, NOM, PRENOM, AGE) values ('dthibau@mymeetingsondemand.com', 'secret','THIBAU','Michel',52);
insert into MEMBER (EMAIL, PASSWORD, NOM, PRENOM, AGE) values ('david.thibau@gmail.com', 'secret','THIBAU','Gaston',67);
insert into MEMBER (EMAIL, PASSWORD, NOM, PRENOM, AGE) values ('toto@titi.com', 'secret','TITI','Toto',40);
insert into MEMBER (EMAIL, PASSWORD, NOM, PRENOM, AGE) values ('tutu@tata.com', 'secret','TATA','Tutu',29);
insert into MEMBER (EMAIL, PASSWORD, NOM, PRENOM, AGE) values ('tete@tonton.com', 'secret','TONTON','Tete',88);

insert into DOCUMENT (NAME, CONTENT_TYPE) values ('scrum.ppt', '	application/vnd.ms-powerpoint');
insert into DOCUMENT (NAME, CONTENT_TYPE) values ('widlfly.odp', 'application/vnd.oasis.opendocument.presentation');
insert into DOCUMENT (NAME, CONTENT_TYPE) values ('JavaEE.pdf','application/pdf');
insert into DOCUMENT (NAME, CONTENT_TYPE) values ('SOAP.xsd','application/xml');
insert into DOCUMENT (NAME, CONTENT_TYPE) values ('Json.txt','text/plain');
insert into DOCUMENT (NAME, CONTENT_TYPE) values ('SpringBoot.yml','text/plain');


insert into member_documents (member_id , documents_id) values (1,1);
insert into member_documents (member_id , documents_id) values (1,2);
insert into member_documents (member_id , documents_id) values (1,3);
insert into member_documents (member_id , documents_id) values (2,4);
insert into member_documents (member_id , documents_id) values (3,5);
insert into member_documents (member_id , documents_id) values (2,6);




