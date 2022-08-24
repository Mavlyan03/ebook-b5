insert into genres(id, name)
values (1, 'Художественная литература'),
       (2,'Книги для детей'),
       (3,'Наука и техника'),
       (4,'Общество'),
       (5,'Деловая литература'),
       (6,'Красота'),
       (7,'Здоровье'),
       (8,'Спорт'),
       (9,'Увлечения'),
       (10,'Психодогия'),
       (11,'Детектив'),
       (12,'Фантастика'),
       (13,'Приключения'),
       (14,'Роман'),
       (15,'Фольклор'),
       (16,'Юмор'),
       (17,'Справочная книга'),
       (18,'Поэзия'),
       (19,'Документальная литература'),
       (20,'Религиозная литература'),
       (21,'Учебная книга'),
       (23,'Зарубежная литература'),
       (24,'Восточная литература'),
       (25,'Русская литература');

insert into users(first_name, last_name, email, phone_number, password, role)
values ('Admin', 'Adminov', 'admin@gmail.com', '+996777777777', '$2a$12$xEFzerKnyLVgXyBQ/ecOjuVs5rDd2KgixXHHvPSIqTN7TDnRH0Oba', 'ADMIN'),
        ('Aichurok', 'Turgunbaeva', 'tasnim0800@gmail.com', '996777454545', 'sdfgfdsxgfd', 'VENDOR' );

insert into books (id, name, author, bestseller, book_status, book_type, price, publishing_house, main_image, is_new, language, owner_id, genre_id, discount, is_enabled, page_size, quantity_of_book, year_of_issue)
values           (1, 'Merry', 'Merry Moulding', true, 1, 1, 1500, 'Quaxo', 'Duis.mov', false, 1, 2, 2,  1, false, 1, 1, 1986),
                  (2, 'dgds', 'vfsfds', false , 2, 2, 1200, 'gfsgd', 'gfdsfds', true , 0, 2 ,1, 2, true , 2, 2, 1682);
