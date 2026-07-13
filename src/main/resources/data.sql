-- =====================================================
-- USUÁRIOS
-- Senha para todos: 123456
-- =====================================================

INSERT INTO usuario
(id, nome, email, senha, role, ativo, data_criacao)
VALUES
(1, 'Administrador', 'admin@delivery.com',
'$2a$10$DW3srwoRfo4srwvYXxhin.dXDsVFZux9fMevr7yyYGBUxYkwCzwe6',
'ADMIN', TRUE, CURRENT_TIMESTAMP),

(2, 'João Silva', 'joao@email.com',
'$2a$10$DW3srwoRfo4srwvYXxhin.dXDsVFZux9fMevr7yyYGBUxYkwCzwe6',
'CLIENTE', TRUE, CURRENT_TIMESTAMP),

(3, 'Maria Souza', 'maria@email.com',
'$2a$10$DW3srwoRfo4srwvYXxhin.dXDsVFZux9fMevr7yyYGBUxYkwCzwe6',
'CLIENTE', TRUE, CURRENT_TIMESTAMP),

(4, 'Pizza Palace', 'pizza@palace.com',
'$2a$10$DW3srwoRfo4srwvYXxhin.dXDsVFZux9fMevr7yyYGBUxYkwCzwe6',
'RESTAURANTE', TRUE, CURRENT_TIMESTAMP),

(5, 'Burger House', 'burger@house.com',
'$2a$10$DW3srwoRfo4srwvYXxhin.dXDsVFZux9fMevr7yyYGBUxYkwCzwe6',
'RESTAURANTE', TRUE, CURRENT_TIMESTAMP),

(6, 'Carlos Entregador', 'carlos@entrega.com',
'$2a$10$DW3srwoRfo4srwvYXxhin.dXDsVFZux9fMevr7yyYGBUxYkwCzwe6',
'ENTREGADOR', TRUE, CURRENT_TIMESTAMP);



-- =====================================================
-- RESTAURANTES
-- =====================================================

INSERT INTO restaurante
(id, nome, categoria, endereco, telefone, taxa_entrega, ativo)
VALUES

(1,
'Pizza Palace',
'Pizzaria',
'Rua das Pizzas, 123',
'(11) 1234-5678',
5.90,
TRUE),

(2,
'Burger House',
'Hamburgueria',
'Av. dos Hambúrgueres, 456',
'(11) 8765-4321',
7.50,
TRUE);



-- =====================================================
-- PRODUTOS
-- =====================================================

INSERT INTO produto
(id, nome, descricao, preco, categoria, disponivel, restaurante_id)
VALUES

(1,
'Pizza Margherita',
'Molho de tomate, mussarela e manjericão',
35.90,
'PIZZA',
TRUE,
1),

(2,
'Pizza Pepperoni',
'Molho de tomate, mussarela e pepperoni',
42.90,
'PIZZA',
TRUE,
1),

(3,
'X-Burger',
'Hambúrguer artesanal com queijo',
24.90,
'LANCHE',
TRUE,
2),

(4,
'Combo X-Burger',
'Hambúrguer, fritas e refrigerante',
34.90,
'COMBO',
TRUE,
2),

(5,
'Refrigerante Lata',
'Coca-Cola 350ml',
6.50,
'BEBIDA',
TRUE,
2),

(6,
'Petit Gateau',
'Bolo de chocolate com sorvete',
18.90,
'SOBREMESA',
TRUE,
1);