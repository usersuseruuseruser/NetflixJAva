insert into genre(name) VALUES
    ('Test1');
insert into genre(name) VALUES
    ('Test2');
insert into genre(name) VALUES
    ('Test3');

insert into content_types (id, content_type_name)
values (1, 'фильм');

insert into ratings(kinopoisk_rating, imdb_rating, local_rating) VALUES
    (5, 5, 5);

insert into subscription (name, description, price) VALUES
    ('Test', 'Test', 100);

insert into trailer_info (url, name) VALUES
    ('https://www.youtube.com/embed/XX0zmgbcMm8?si=vuh7Lw3pU8EpZgqh', 'Трейлер');

insert into movies (name, description, slogan, poster_url, country, content_type_id, trailer_info_id, ratings_id, release_date)
values ('Test', 'Test Test Test Test Test Test Testst Test Test Test Test Test Test Tesst Test Test Test Test Test Test Tesst Test Test Test Test Test Test Tesst Test Test Test Test Test Test Tesst Test Test Test Test Test Test Tesst Test Test Test Test Test Test Tesst Test Test Test Test Test Test Tes Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test ', 'Test',
        'https://image.openmoviedb.com/kinopoisk-images/1898899/8ef070c9-2570-4540-9b83-d7ce759c0781/orig',
        'Россия', 1, 1, 1, '2021-06-01');

insert into movies_allowed_subscriptions (movie_id, allowed_subscriptions_id) VALUES
    (1, 1);

insert into movies_genres (movie_id, genres_id) VALUES
    (1, 1);
insert into movies_genres (movie_id, genres_id) VALUES
    (1, 2);

insert into subscription_movies (subscription_id, movies_id) VALUES
    (1, 1);
