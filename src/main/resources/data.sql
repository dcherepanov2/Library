INSERT INTO author(id, name, slug, photo, description) SELECT 1, 'name', '123123', 'src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg', 'description' WHERE NOT EXISTS(SELECT id FROM author WHERE id = 1);
INSERT INTO users SELECT 1, '123123', to_date('1-9-2011', 'DD-MM-YYYY'), 0.00, 'danilcherep7@gmail.com' WHERE NOT EXISTS (SELECT id FROM users WHERE id = 1);
INSERT INTO roles(id, name) SELECT 1, 'USER_CLIENT' WHERE NOT EXISTS(SELECT id FROM roles WHERE id = 1);
INSERT INTO user_roles(user_id, role_id) SELECT 1, 1 WHERE NOT EXISTS(SELECT user_id FROM user_roles WHERE user_id = 1);
INSERT INTO user_contact SELECT 1,1, 1, 1, '123123', 0, to_date('1-9-2011', 'DD-MM-YYYY'), 'danilcherep7@gmail.com' WHERE NOT EXISTS(SELECT id FROM user_contact WHERE id = 1);
INSERT INTO book(id, is_bestseller, slug, title, image, description, price, pub_date, discount) SELECT 1, 1, 'book-kzx-285', 'title', 'src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg', 'description', 100, to_date('01-09-2011', 'DD-MM-YYYY'), 100 WHERE NOT EXISTS(SELECT id FROM book WHERE id = 1) AND NOT EXISTS(SELECT slug FROM book WHERE slug = 'book-kzx-285');
INSERT INTO book(id, is_bestseller, slug, title, image, description, price, pub_date, discount) SELECT 2, 1, 'book-kzx-286', 'title', 'src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg', 'description', 100, to_date('01-09-2011', 'DD-MM-YYYY'), 100 WHERE NOT EXISTS(SELECT id FROM book WHERE id = 2) AND NOT EXISTS(SELECT slug FROM book WHERE slug = 'book-kzx-286');
INSERT INTO book(id, is_bestseller, slug, title, image, description, price, pub_date, discount) SELECT 3, 1, 'book-kzx-287', 'title', 'src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg', 'description', 100, to_date('01-09-2011', 'DD-MM-YYYY'), 100 WHERE NOT EXISTS(SELECT id FROM book WHERE id = 3) AND NOT EXISTS(SELECT slug FROM book WHERE slug = 'book-kzx-287');
INSERT INTO author(id, name, slug, photo, description) SELECT 1, 'name', 'book-svi-028', 'http://dummyimage.com/374x416.png/ff4444/ffffff', 'description' WHERE NOT EXISTS(SELECT id FROM author WHERE id = 1);
INSERT INTO book2author(id, book_id, authors_id, sort_index) SELECT 1, 1, 1, 1 WHERE NOT EXISTS(SELECT id FROM book2author WHERE id = 1);
INSERT INTO book2author(id, book_id, authors_id, sort_index) SELECT 2, 2, 1, 1 WHERE NOT EXISTS(SELECT id FROM book2author WHERE id = 2);
INSERT INTO book2author(id, book_id, authors_id, sort_index) SELECT 3, 3, 1, 1 WHERE NOT EXISTS(SELECT id FROM book2author WHERE id = 3);
INSERT INTO tag SELECT 1, 'name', 'tag-121', 4, 0 WHERE NOT EXISTS(SELECT id FROM tag WHERE id = 1);
INSERT INTO book2tag SELECT 1, 1, 1 WHERE NOT EXISTS(SELECT id FROM book2tag WHERE id = 1);
INSERT INTO book2tag SELECT 2, 2, 1 WHERE NOT EXISTS(SELECT id FROM book2tag WHERE id = 2);
INSERT INTO book2tag SELECT 3, 3, 1 WHERE NOT EXISTS(SELECT id FROM book2tag WHERE id = 3);
INSERT INTO book_file SELECT 1, '123', 1, 0.00, 'file.epub', 2 WHERE NOT EXISTS(SELECT id FROM book_file WHERE id = 1);
INSERT INTO file_download SELECT 1,1,1,1 WHERE NOT EXISTS(SELECT id FROM file_download WHERE id = 1);
INSERT INTO book_file SELECT 2, '1234', 2, 0.00, 'file.fb2', 2 WHERE NOT EXISTS(SELECT id FROM book_file WHERE id = 2);
INSERT INTO book_file SELECT 3, '12345', 3, 0.00, 'file.pdf', 2 WHERE NOT EXISTS(SELECT id FROM book_file WHERE id = 3);
INSERT INTO file_download SELECT 2,1,1,1 WHERE NOT EXISTS(SELECT id FROM file_download WHERE id = 2);
INSERT INTO book2file SELECT 1, 2, 1 WHERE NOT EXISTS(SELECT id FROM book2file WHERE id = 1);
INSERT INTO book2file SELECT 2, 2, 1 WHERE NOT EXISTS(SELECT id FROM book2file WHERE id = 2);
INSERT INTO book2file SELECT 3, 2, 1 WHERE NOT EXISTS(SELECT id FROM book2file WHERE id = 3);