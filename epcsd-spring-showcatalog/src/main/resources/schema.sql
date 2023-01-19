INSERT INTO category (name, description)
VALUES ('Teatro', 'Obras de teatro, musicales, drama, comedia...')
     , ('Concierto', 'Conciertos de música clásica, pop, rock, etc.')
     , ('Danza', 'Representaciones de danza clásica, contemporánea...')
     , ('Ópera', 'Ópera')
     , ('Circo', 'Actuaciones de circo')
     , ('Monólogo', 'Monólogos, stand-up comedy, improvisación, etc.');

INSERT INTO show (name, id_category, description, image, price, duration, capacity, on_sale_date, status)
VALUES ('Cats', 1, 'Musical Cats', 'https://upload.wikimedia.org/wikipedia/en/3/3e/CatsMusicalLogo.jpg', 20,
        120, 300, '2022-05-01', 0)
     , ('Macbeth', 1, 'MacBeth by Shakespeare', 'https://upload.wikimedia.org/wikipedia/commons/4/40/First-page-first-folio-macbeth.jpg',
        30, 180, 350, '2022-06-15', 0)
     , ('Concierto de año nuevo', 2, 'Concierto de música clásica con ocasión del año nuevo 2023',
        'https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2021/12/28/16407217716248.jpg', 15, 120, 250, '2022-11-01', 0)
     , ('Las 4 estaciones', 2, 'Las 4 estaciones de Vivaldi', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Antonio_Vivaldi.jpg/220px-Antonio_Vivaldi.jpg', 17, 90, 200, '2022-11-01', 0)

     , ('El lago de los cisnes', 3, 'Interpretado por la compañía de danza de San Petersburgo',
        'https://upload.wikimedia.org/wikipedia/commons/9/93/Wilfride_Piollet_danse_dans_%22le_Lac_des_Cygnes%22_%28%C3%A0_l%27Op%C3%A9ra_de_Paris%2C_1977%29.jpg',
        40, 180, 325, '2022-12-05', 0)
     , ('El cascanueces', 3, 'Interpretado por la compañía de danza de Sebastopol', 'https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/Nutcracker_-Scene_from_Act_I_-Sergei_Legat_as_Nutcracker%2C_Stanislava_Stanislavovna_Belinskaya_as_Clara%2C_%26_Unidentified_as_a_Gingerbread_Soldier_-1892.jpg/250px-Nutcracker_-Scene_from_Act_I_-Sergei_Legat_as_Nutcracker%2C_Stanislava_Stanislavovna_Belinskaya_as_Clara%2C_%26_Unidentified_as_a_Gingerbread_Soldier_-1892.jpg', 38, 120,
        300, '2022-09-18', 0)

     , ('Così fan tutte', 4, 'Ciclo Mozart en el Palau de la Musica',
        'https://es.wikipedia.org/wiki/Cos%C3%AC_fan_tutte#/media/Archivo:Cosi_fan_tutte_-_first_performance.jpg', 24,
        110, 225, '2023-01-12', 0)
     , ('Rigoletto', 4, 'Verdi al Liceu', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Rigoletto_premiere_poster.jpg/245px-Rigoletto_premiere_poster.jpg', 42, 100, 150, '2022-08-01', 0)

     , ('Circo Raluy', 5, 'El Circo Raluy visita Barcelona', 'https://raluy.com/wp-content/uploads/2021/01/inter2-copia.jpg', 10, 120, 400, '2022-06-01', 0)
     , ('Circo Mundial', 5, 'Gira de verano del Circo Mundial - Ahora SIN leones!', 'http://www.grancircomundial.com/wp-content/uploads/2015/10/foto-1.jpg', 12, 100,
        300, '2022-08-10', 0)

     , ('Vermunólogos', 6, 'Diferentes artistas cada día!', 'https://www.teatrebarcelona.com/wp-content/uploads/2015/01/TEATRE_BARCELONA-Vermunologos.png', 5, 45, 100, '2022-06-15', 0)
     , ('Tinder sorpresa', 6, 'El nuevo espectáculo de Toni Moog', 'https://vmanager.iseic.net/media/ver/cartell/v3/315E0/01_085_00054470/Cartel-web-tinder-sorpresa-teatre-goya.jpg', 15, 90, 200,
        '2022-07-20', 0)
     , ('El club de la comedia', 6, 'Actuación en vivo del aclamado programa de humor', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/El_Club_de_la_Comedia.svg/253px-El_Club_de_la_Comedia.svg.png', 20,
        90, 300, '2022-10-05', 0)
;


INSERT INTO performance (id_show, date, time, streaming_url, remaining_seats, status)
values (1, '2022-05-15', '21:30', 'http://foo.bar', 300, 0)
     , (1, '2022-05-18', '21:30', 'http://foo.bar', 300, 0)
     , (2, '2022-07-25', '22:00', 'http://foo.bar', 350, 0)
;

INSERT INTO show_comments (id_show, comment, rating)
values (1, 'Excelente musical', 2)
     , (1, 'De los mejores musicales que he visto', 4)
     , (2, 'Es un lujo empezar el año con este concierto de música clásica', 3)
;

INSERT INTO company (name, address, email, mobile_number)
values ('Teatro Apolo', 'Av. del Paral·lel, 59, 08004 Barcelona', 'taquilla@teatroapolo.com', '667098231')
     , ('Teatro Lara', 'Corre. Baja de San Pablo, 15, 28004 Madrid', 'info@teatrolara.org', '621786532')
     , ('Teatro Lope de Vega', 'Av. de María Luisa, s/n, 41013 Sevilla', 'info@teatrolopedevega.org', '617348976')
;