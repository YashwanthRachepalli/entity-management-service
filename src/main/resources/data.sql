INSERT INTO ADDRESS (address_id, address_line_1, address_line_2, pin_code)
VALUES ('6a17ce18-94dd-40ac-bb11-2e838bc50b7e', 'Lane 1', 'Landmark', '999999');

INSERT INTO TENANT (tenant_id, first_name, last_name, password, address_id, govt_issued_identifier, email, mobile_number )
VALUES ('5cadcc39-d58a-40f9-af81-244eee152254', 'Andy', 'Fletcher', 'xxxx', '6a17ce18-94dd-40ac-bb11-2e838bc50b7e', 'xxxx', 'xxx@gmail.com', 'xxxxxxxx');

INSERT INTO BUILDING (building_id, building_name, address_line, pin_code)
VALUES ('8cd6b720-6ef5-4bbb-b76b-a8912573eca5', 'xxx xxx xxx', 'Line 1 near landmark', 'xxxxxx');

INSERT INTO APARTMENT (apartment_id, apartment_name, apartment_size, apartment_type, building_id)
VALUES ('03dfdeeb-e2d0-4cf6-8f75-dc374be2129f', 'A301', 1800, 'FOUR_BHK', '8cd6b720-6ef5-4bbb-b76b-a8912573eca5');

INSERT INTO APARTMENT (apartment_id, apartment_name, apartment_size, apartment_type, building_id)
VALUES ('913b65e1-aa51-4e05-8305-2f2bfd6dd63c', 'A302', 2250, 'FOUR_BHK', '8cd6b720-6ef5-4bbb-b76b-a8912573eca5');

INSERT INTO LEASE (lease_id, apartment_id, tenant_id, start_date, end_date, rental_date, rental_fee, security_deposit)
VALUES ('d9b6342e-7078-4484-a98a-0ea554412808', '03dfdeeb-e2d0-4cf6-8f75-dc374be2129f', '5cadcc39-d58a-40f9-af81-244eee152254', '2021-12-20', '2024-12-20', '2021-01-01', 25000.00, 200000.00);