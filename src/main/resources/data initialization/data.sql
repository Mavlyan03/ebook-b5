insert into genres(id, name)
values (1, 'Художественная литература'),
       (2, 'Книги для детей'),
       (3, 'Наука и техника'),
       (4, 'Общество'),
       (5, 'Деловая литература'),
       (6, 'Красота'),
       (7, 'Здоровье'),
       (8, 'Спорт'),
       (9, 'Увлечения'),
       (10, 'Психология'),
       (11, 'Детектив'),
       (12, 'Фантастика'),
       (13, 'Приключения'),
       (14, 'Роман'),
       (15, 'Фольклор'),
       (16, 'Юмор'),
       (17, 'Справочная книга'),
       (18, 'Поэзия'),
       (19, 'Документальная литература'),
       (20, 'Религиозная литература'),
       (21, 'Учебная книга'),
       (23, 'Зарубежная литература'),
       (24, 'Восточная литература'),
       (25, 'Русская литература');

insert into users(id, created_at, first_name, last_name, email, phone_number, password, role)
values (1, '2022-08-22', 'Admin', 'Adminov', 'admin@gmail.com', '+996777777777', '$2a$12$xEFzerKnyLVgXyBQ/ecOjuVs5rDd2KgixXHHvPSIqTN7TDnRH0Oba', 'ADMIN'),
       (2, '2022-08-22', 'Vendor', 'Vendorov', 'vendor@gmail.com', '+996777888999', '$2a$12$L00bQs1P2eCIQTvcqnjJcej1cWAAx1WoBUELicn8PwD.9nHgcRHwK', 'VENDOR'),
       (3, '2022-08-22', 'User', 'Userov', 'user@gmail.com', '+996555666777', '$2a$12$dki5VjuWI6BAkkVFfujNZuMOvkSSvnthppJBR.PE2b7dyIaXD6hJ.', 'USER');

insert into books(id, audio_book, audio_book_fragment, author, bestseller, book_status, book_type, date_the_book_was_added_to_favorites, description, discount, duration, electronic_book, fragment, is_enabled, is_new, language, main_image, name, page_size, price, published_date, publishing_house, quantity_of_book, second_image, third_image, year_of_issue, genre_id, owner_id)
VALUES (1, 'audio book', 'audio book fragment', 'Чынгыз Айтматов', true, 'IN_PROCESSING', 'AUDIO_BOOK', '2022-08-22', 'Ысыккөл кээде тынч да, кээде толкун,', 0, '01:02:34', null, null, false, false, 'KYRGYZ', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611702063041024w-iRBldJ_jyLw.webp', 'Ыссык-Кол', 284, 500, null, 'Аркус', 1, 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611704625357-2.png', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/1661170736047images.jpg', 1995, 1, 2),
       (2, 'audio book', 'audio book fragment', 'Чынгыз Айтматов', true, 'IN_PROCESSING', 'AUDIO_BOOK', '2022-08-22', 'Ысыккөл кээде тынч да, кээде толкун,', 12, '01:02:34', null, null, false, false, 'KYRGYZ', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611702063041024w-iRBldJ_jyLw.webp', 'Ыссык-Кол', 284, 500, null, 'Аркус', 1, 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611704625357-2.png', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/1661170736047images.jpg', 1995, 1, 2),
       (3, null, null, 'Чынгыз Айтматов', true, 'ACCEPTED', 'ELECTRONIC_BOOK', '2022-08-22', 'Толкуса толкунуна тен ортокмун.', 15, null, 'https://ebookjava5.s3.eu-central-1.amazonaws.com/1661177906416-pdf-531-.jpg', 'Турмушта канча жолдош күтсөм дагы,', false, false, 'RUSSIAN', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611702063041024w-iRBldJ_jyLw.webp', 'Баткен', 234, 450,'2022-08-22', 'Аркус', 2, 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611704625357-2.png', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/1661170736047images.jpg', 2010, 2, 2),
       (4, null, null, 'Чынгыз Айтматов', true, 'ACCEPTED', 'ELECTRONIC_BOOK', '2022-08-22', 'Толкуса толкунуна тен ортокмун.', 0, null, 'https://ebookjava5.s3.eu-central-1.amazonaws.com/1661177906416-pdf-531-.jpg', 'Турмушта канча жолдош күтсөм дагы,', false, false, 'RUSSIAN', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611702063041024w-iRBldJ_jyLw.webp', 'Баткен', 234, 450,'2022-08-22', 'Аркус', 1, 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611704625357-2.png', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/1661170736047images.jpg', 2015, 5, 2),
       (5, null, null, 'Чынгыз Айтматов', false, 'REJECTED', 'PAPER_BOOK', '2022-08-23', 'Бир сырдуу сендей жолдош күткөн жокмун.', 0, null, null, 'Адамга эн кыйын кун сайын адам болуу', true, false, 'ENGLISH', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611702063041024w-iRBldJ_jyLw.webp', 'Талас', 123, 495, null, 'Аркус', 3, 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611704625357-2.png', 'https://ebookjava5.s3.eu-central-1.amazonaws.com/1661170736047images.jpg', 2020, 3, 2);

insert into users_favorite_books(book_id, user_id)
VALUES (3, 3),
       (4, 3);

insert into users_basket_books(book_id, user_id)
VALUES (3, 3),
       (4, 3);

insert into promocodes(id, name, date_of_start, date_of_finish, discount , vendor_id)
values (1,'Промо', '2022-08-23', '2022-09-23', 15, 2);

insert into purchased_user_books(id, book_author, book_id, book_main_image, book_name, price, promocode, purchase_date, quantity_of_book, user_id)
values (1, 'Чынгыз Айтматов', 3, 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611702063041024w-iRBldJ_jyLw.webp', 'Биринчи мугалим', 355, 15, '2022-08-24', 1, 3),
       (2, 'Чынгыз Айтматов', 4, 'https://ebookjava5.s3.eu-central-1.amazonaws.com/16611702063041024w-iRBldJ_jyLw.webp', 'Биринчи мугалим', 355, 0, '2022-08-24', 1, 3);

insert into notifications(id, book_id, book_status, created_at, description, read, vendor_id)
values (1, 2, 'ACCEPTED', '2022-08-24', null, false, 2),
       (2, 3, 'REJECTED', '2022-08-24', 'Книга не соответствует', false, 2);
