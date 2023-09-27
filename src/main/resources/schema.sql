create table ADDRESS
(
    address_id uuid not null,
    address_line_1 varchar(255) not null,
    address_line_2 varchar(255),
    pin_code varchar(255) not null,
    primary key (address_id)
);

create table TENANT
(
    tenant_id uuid not null,
    first_name varchar(255) not null,
    last_name varchar(255),
    password varchar(255) not null,
    address_id uuid,
    govt_issued_identifier varchar(255) not null,
    email varchar(255) not null,
    mobile_number varchar(255) not null,
    preferred_contact varchar(255) not null,
    check(preferred_contact in ('SMS', 'EMAIL')),
    primary key (tenant_id),
    foreign key (address_id) references ADDRESS (address_id)
);

create table BUILDING
(
   building_id uuid not null,
   building_name varchar(255) not null,
   address_line varchar(255) not null,
   pin_code varchar(255) not null,
   primary key (building_id)
);

create table APARTMENT
(
   apartment_id uuid not null,
   apartment_size int not null,
   apartment_name varchar(36) not null,
   apartment_type varchar(255),
   building_id uuid not null,
   primary key (apartment_id),
   check (apartment_type in ('SINGLE_BHK', 'TWO_BHK', 'TWO_AND_HALF_BHK', 'THREE_BHK', 'FOUR_BHK')),
   foreign key (building_id) references BUILDING (building_id),
   unique (apartment_name)
);

create table LEASE (
    lease_id uuid not null,
    apartment_id uuid not null,
    tenant_id uuid not null,
    start_date date not null,
    end_date date not null,
    rental_date date not null,
    security_deposit decimal(9,2) not null,
    rental_fee decimal(9,2) not null,
    primary key (lease_id),
    foreign key (apartment_id) references APARTMENT (apartment_id),
    foreign key (tenant_id) references TENANT (tenant_id)
);

create table VISITOR
(
    visitor_id uuid not null,
    first_name varchar(255) not null,
    last_name varchar(255),
    govt_issued_identifier varchar(255) not null,
    mobile_number varchar(255) not null,
    primary key (visitor_id)
);