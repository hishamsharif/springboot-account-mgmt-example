 drop table if exists accounts CASCADE ;
 drop table if exists customer_accounts CASCADE  ;
 drop table if exists customers CASCADE ; 
 drop sequence if exists acc_seq ;
 drop sequence if exists cust_seq;
 
 create sequence acc_seq start with 1000 increment by 50;
 create sequence cust_seq start with 100 increment by 50;
 
 create table accounts (
       acc_id bigint not null,
        acc_no varchar(255),
        balance double not null,
        primary key (acc_id)
    );
 
 create table customer_accounts (
       cust_id bigint not null,
        acc_id bigint not null,
        primary key (cust_id, acc_id)
    );
 
 create table customers (
       cust_id bigint not null,
        email varchar(255),
        name varchar(255),
        primary key (cust_id)
    );
 
 alter table accounts 
       add constraint UK_3utl50x1x09lyg4ov6v6yxsb4 unique (acc_no);

 alter table customers 
       add constraint UK_rfbvkrffamfql7cjmen8v976v unique (email);

 alter table customer_accounts 
       add constraint FKrfwiexoduxyllk4q9khcjtu85 
       foreign key (acc_id) 
       references accounts;

 alter table customer_accounts 
       add constraint FKsepwtiqdc1c5xlil61nkp6bt9 
       foreign key (cust_id) 
       references customers;