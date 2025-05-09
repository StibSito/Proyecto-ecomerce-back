DROP DATABASE IF EXISTS ECOMERCE_CIBERTEC;

CREATE DATABASE ECOMERCE_CIBERTEC;

USE ECOMERCE_CIBERTEC;



create table tb_tipos(
idtipo int not null primary key,
descripcion varchar(15)
);

create table tb_estados(
idestado    int not null primary key,
descripcion varchar(15)
);

CREATE TABLE tb_cuentas (
codigo  int auto_increment,
nombre varchar(15),
apellido varchar(25),
email  char(45) NOT NULL,
celular char(9) not null,
clave    char(5),
fnacim  date  null,
tipo    int DEFAULT 2,
estado  int DEFAULT 1,
primary key (codigo),
foreign key (tipo) references tb_tipos(idtipo),
foreign key (estado) references tb_estados(idestado)
);

CREATE TABLE tb_categorias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(255) NOT NULL
);


CREATE TABLE tb_productos (
idprod char(5) not null,
descripcion varchar(45),
stock int,
precio decimal(8,2),
idcategoria int,
estado int,
primary key (idprod), 
foreign key (idcategoria) references tb_categorias(id)
);



CREATE TABLE tb_ordenes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    total DECIMAL(10, 2) NOT NULL,
    date DATETIME NOT NULL,
    FOREIGN KEY (account_id) REFERENCES tb_cuentas(codigo)
);

CREATE TABLE tb_carrito (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id char(5) not null,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES tb_productos(idprod)
);

create table tb_cab_boleta(
num_bol      char(5) not null,
fch_bol    date,
cod_cliente  int not null,
primary key (num_bol),
foreign key (cod_cliente) references tb_cuentas(codigo)
);

create table tb_det_boleta(
num_bol     char(5) not null,
idprod      char(5) not null,
cantidad    int,
preciovta   decimal(8,2),
primary key (num_bol,idprod),
foreign key (num_bol) references tb_cab_boleta(num_bol),
foreign key (idprod) references tb_productos(idprod)
);

-- inserts
insert into tb_tipos values (1, 'administrativo');
insert into tb_tipos values (2, 'cliente');

insert into tb_estados values (1, 'activo');
insert into tb_estados values (2, 'eliminado');
insert into tb_estados values (3, 'suspendido');


insert into tb_categorias values (1, 'Computadoras');
insert into tb_categorias values (2, 'Laptops');
insert into tb_categorias values (3, 'Almacenamiento');
insert into tb_categorias values (4, 'Audifonos');
insert into tb_categorias values (5, 'Cases');
insert into tb_categorias values (6, 'Fuentes de Poder');
insert into tb_categorias values (7, 'Mouse');
insert into tb_categorias values (8, 'Placas Madre');
insert into tb_categorias values (9, 'Procesadores');
insert into tb_categorias values (10, 'Memoria RAM');
insert into tb_categorias values (11, 'Tarjetas de Video');
insert into tb_categorias values (12, 'Teclados');

insert into tb_cuentas values(null,"Stib","Diaz","diaz@gmail.com","985126595","12345",curDate(),default,default);

-- aqui inserta los productos 
-- ejemplo insert into tb_productos values("P0001","Pc 2001",100,1500.00,1,1);
insert into tb_productos values("P0001","Tarjeta de Video ",50,2000.00,11,1);
insert into tb_productos values("P0002","Tarjeta de Video ",100,1000.00,11,1);
insert into tb_productos values("P0003","Tarjeta de Video ",100,1900.50,11,1);
insert into tb_productos values("P0004","Mouse ",100,160.00,7,1);
insert into tb_productos values("P0005","Mouse ",100,90.00,7,1);
insert into tb_productos values("P0006","Mouse ",100,100.00,7,1);
insert into tb_productos values("P0007","Mouse ",100,99.00,7,1);
insert into tb_productos values("P0008","Teclados ",100,175.00,12,1);
insert into tb_productos values("P0009","Teclados ",100,205.00,12,1);
insert into tb_productos values("P0010","Teclados ",100,985.50,12,1);
insert into tb_productos values("P0011","Teclados ",100,208.00,12,1);
insert into tb_productos values("P0012","Teclados ",100,200.00,12,1);
insert into tb_productos values("P0013","Placas Madre ",100,2000.00,8,1);
insert into tb_productos values("P0014","Placas Madre ",100,2500.00,8,1);
insert into tb_productos values("P0015","Placas Madre ",100,3000.00,8,1);
insert into tb_productos values("P0016","Placas Madre ",100,1800.00,8,1);
insert into tb_productos values("P0017","Placas Madre ",100,4000.00,8,1);
insert into tb_productos values("P0018","Procesador ",100,600.00,9,1);
insert into tb_productos values("P0019","Procesador ",100,2000.00,9,1);
insert into tb_productos values("P0020","Procesador ",100,1540.00,9,1);
insert into tb_productos values("P0021","Procesador ",100,1700.00,9,1);
insert into tb_productos values("P0022","Memoria RAM ",75,130.00,10,1);
insert into tb_productos values("P0023","Memoria RAM ",75,250.00,10,1);
insert into tb_productos values("P0024","Memoria RAM ",75,145.00,10,1);
insert into tb_productos values("P0025","Memoria RAM ",75,170.00,10,1);
insert into tb_productos values("P0026","Memoria RAM ",75,200.00,10,1);
insert into tb_productos values("P0027","Memoria RAM ",75,400.00,10,1);
insert into tb_productos values("P0028","Memoria RAM ",75,350.00,10,1);
insert into tb_productos values("P0029","Almacenamiento ",200,190.00,3,1);
insert into tb_productos values("P0030","Almacenamiento ",200,270.00,3,1);
insert into tb_productos values("P0031","Audifonos ",500,70.00,4,1);
insert into tb_productos values("P0032","Audifonos ",500,190.00,4,1);
insert into tb_productos values("P0033","Audifonos ",500,190.00,4,1);
insert into tb_productos values("P0034","Audifonos ",500,650.00,4,1);
insert into tb_productos values("P0035","Cases ",250,200.00,5,1);
insert into tb_productos values("P0036","Cases ",250,250.00,5,1);
insert into tb_productos values("P0037","Cases ",250,300.00,5,1);
insert into tb_productos values("P0038","Cases ",250,400.00,5,1);
insert into tb_productos values("P0039","Fuentes de Poder ",450,249.00,6,1);
insert into tb_productos values("P0040","Fuentes de Poder ",450,149.00,6,1);
insert into tb_productos values("P0041","Fuentes de Poder ",450,215.00,6,1);
insert into tb_productos values("P0042","Fuentes de Poder ",450,300.00,6,1);
insert into tb_productos values("P0043","Fuentes de Poder ",450,249.00,6,1);
insert into tb_productos values("P0044","Computadoras ",2000,2079.00,1,1);
insert into tb_productos values("P0045","Computadoras ",2000,9072.00,1,1);
insert into tb_productos values("P0046","Computadoras ",2000,7192.00,1,1);
insert into tb_productos values("P0047","Computadoras ",2000,8073.00,1,1);
insert into tb_productos values("P0048","Computadoras ",2000,3984.00,1,1);
insert into tb_productos values("P0049","Laptops ",50,4436.00,2,1);
insert into tb_productos values("P0050","Laptops ",50,3045.00,2,1);
insert into tb_productos values("P0051","Laptops ",50,2956.00,2,1);
insert into tb_productos values("P0052","Laptops ",50,6993.00,2,1);
insert into tb_productos values("P0053","Laptops ",50,1794.00,2,1);



-- crear procedimiento
create procedure us_validar(v_usr varchar(45),vs_cla varchar(5))
select * from tb_cuentas where email = v_usr and clave = vs_cla;

create procedure usp_regprod(v_cod char(5),v_nom varchar(45),v_stk int,v_pre decimal(8,2),v_cat int,v_est int)
insert into tb_productos values (v_cod ,v_nom ,v_stk ,v_pre ,v_cat ,v_est);

DELIMITER //
CREATE PROCEDURE actualizar_producto(
    IN p_codigo CHAR(5),
    IN p_descripcion VARCHAR(45),
    IN p_stock INT,
    IN p_precio DECIMAL(8,2),
    IN p_idcategoria INT,
    IN p_estado INT
)
BEGIN
    UPDATE tb_productos
    SET descripcion = p_descripcion,
        stock = p_stock,
        precio = p_precio,
        idcategoria = p_idcategoria,
        estado = p_estado
    WHERE idprod = p_codigo;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_mostrarProductos()
BEGIN
    SELECT 
        p.idprod AS idprod,
        p.descripcion AS desc_prod,
        p.precio AS precio,
        c.descripcion AS desc_cate,
        p.stock AS stock
    FROM 
        tb_productos p
    INNER JOIN 
        tb_categorias c ON p.idcategoria = c.id
    ORDER BY 
        p.idprod; -- Ordena los resultados por el campo idprod
END //
DELIMITER ;




call us_validar("diaz@gmail.com","12345");
call usp_regprod('P0054','Mascarilla KN95 cj 10',200,25.0,5,1);
call sp_mostrarProductos();


select * from tb_cuentas;
select * from tb_estados;
select * from tb_tipos;
select * from tb_categorias;
select * from tb_productos;
select * from tb_det_boleta;
select * from tb_cab_boleta



