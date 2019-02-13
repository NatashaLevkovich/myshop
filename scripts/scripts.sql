CREATE SCHEMA myshop;

ALTER SCHEMA `myshop`  DEFAULT CHARACTER SET utf8  DEFAULT COLLATE utf8_unicode_ci ;

use myshop;

create table product
(
  id           bigint auto_increment
    primary key,
  category     varchar(255)  null,
  discount     double        null,
  image        varchar(255)  null,
  manufacturer varchar(255)  null,
  material     varchar(255)  null,
  name         varchar(255)  null,
  price        double(10, 2) null,
  subcategory  varchar(255)  null
);

create table product_sizeandquantity
(
  Product_id          bigint not null,
  sizeAndQuantity     int    null,
  sizeAndQuantity_KEY int    not null,
  primary key (Product_id, sizeAndQuantity_KEY),
  constraint product_sizeandquantity.FK8v4v1umffu3ldbaacmsv4xrwm
  foreign key (Product_id) references product (id)
);

create table user
(
  id          bigint auto_increment
    primary key,
  address     varchar(255) null,
  email       varchar(255) null,
  firstName   varchar(255) null,
  lastName    varchar(255) null,
  password    varchar(255) null,
  phoneNumber varchar(255) null,
  status      varchar(255) null,
  constraint user.UK_e6gkqunxajvyxl5uctpl2vl2p
  unique (email)
);

create table t_order
(
  id         bigint auto_increment
    primary key,
  orderDate  datetime      null,
  status     varchar(255)  null,
  totalPrice double(10, 2) null,
  user_id    bigint        null,
  constraint FK8ltat84rmw3k2fbyrut1jylrb
  foreign key (user_id) references user (id)
);

create table item
(
  id          bigint auto_increment
    primary key,
  discount    double        null,
  price       double(10, 2) null,
  productSize int           null,
  quantity    int           null,
  order_id    bigint        null,
  product_id  bigint        null,
  constraint FK7es7nv030p5cr5jh6lj8lsuu3
  foreign key (order_id) references t_order (id),
  constraint FKt9no2hebi43y3umpo8xj3fadr
  foreign key (product_id) references product (id)
);

create index FK7es7nv030p5cr5jh6lj8lsuu3
  on item (order_id);

create index FKt9no2hebi43y3umpo8xj3fadr
  on item (product_id);

create index FK8ltat84rmw3k2fbyrut1jylrb
  on t_order (user_id);

INSERT INTO user (id, address, email, firstName, lastName, password, phoneNumber, status) VALUES (2, 'г. Минск', '123@mail.com', 'Наталья', '', '$2a$10$XVxc3VEzryl/esJSRWPCGOXNxHVBC7wMsXy1YYqfq0gzE4OGO48B6', '', 'USER');
INSERT INTO user (id, address, email, firstName, lastName, password, phoneNumber, status) VALUES (3, '', 'nata@mail.com', 'Nata', '', '$2a$10$mpZkRX7ddFnfKC6QG8dgyelVtC5bkqL/L8GzxLAiPslR/Lq9FatiW', '', 'ADMIN');
INSERT INTO user (id, address, email, firstName, lastName, password, phoneNumber, status) VALUES (5, '', '12@mail.com', 'Nata', '', '$2a$10$ttnvmDGIUfdNLDHClRqAZuU1YOEszzOHlhVm.y./MVWLaDILwzJSe', '', 'USER');

INSERT INTO product (id, category, discount, image, manufacturer, material, name, price, subcategory) VALUES (3, '', 0.3, 'assets/image/products/bg.jpg', '', '', 'джинсы', 5, 'BOYS');
INSERT INTO product (id, category, discount, image, manufacturer, material, name, price, subcategory) VALUES (4, 'CLOTHES', 0, 'assets/image/products/bg.jpg', null, null, 'майка белая', 4.25, 'girls');
INSERT INTO product (id, category, discount, image, manufacturer, material, name, price, subcategory) VALUES (5, 'CLOTHES', 0, 'assets/image/products/bg.jpg', null, null, 'комплект', 9.99, 'newborn');
INSERT INTO product (id, category, discount, image, manufacturer, material, name, price, subcategory) VALUES (6, 'CLOTHES', 0.3, 'assets/image/products/bg.jpg', null, null, 'майка для девочки', 4.5, 'girls');
INSERT INTO product (id, category, discount, image, manufacturer, material, name, price, subcategory) VALUES (7, 'CLOTHES', 0.3, 'assets/image/products/bg.jpg', null, null, 'майка для мальчика', 5, 'boys');
INSERT INTO product (id, category, discount, image, manufacturer, material, name, price, subcategory) VALUES (8, '', 0.3, 'assets/image/products/bg.jpg', '', '', 'джинсы', 10, 'NEWBORN');
INSERT INTO product_sizeandquantity (Product_id, sizeAndQuantity, sizeAndQuantity_KEY) VALUES (3, 10, 104);
INSERT INTO product_sizeandquantity (Product_id, sizeAndQuantity, sizeAndQuantity_KEY) VALUES (3, 10, 110);
INSERT INTO product_sizeandquantity (Product_id, sizeAndQuantity, sizeAndQuantity_KEY) VALUES (4, 5, 110);
INSERT INTO product_sizeandquantity (Product_id, sizeAndQuantity, sizeAndQuantity_KEY) VALUES (5, 5, 100);
INSERT INTO product_sizeandquantity (Product_id, sizeAndQuantity, sizeAndQuantity_KEY) VALUES (6, 5, 100);
INSERT INTO product_sizeandquantity (Product_id, sizeAndQuantity, sizeAndQuantity_KEY) VALUES (7, 5, 110);
INSERT INTO product_sizeandquantity (Product_id, sizeAndQuantity, sizeAndQuantity_KEY) VALUES (8, 10, 92);
INSERT INTO product_sizeandquantity (Product_id, sizeAndQuantity, sizeAndQuantity_KEY) VALUES (8, 10, 98);
INSERT INTO product_sizeandquantity (Product_id, sizeAndQuantity, sizeAndQuantity_KEY) VALUES (8, 10, 104);

