--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.4 (Debian 17.4-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: ingredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingredients (
    ingredient_id integer NOT NULL,
    category character varying(255) NOT NULL,
    ingredient_name character varying(255) NOT NULL,
    CONSTRAINT ingredients_category_check CHECK (((category)::text = ANY ((ARRAY['MEAT'::character varying, 'FISH'::character varying, 'VEGETABLE'::character varying, 'FRUIT'::character varying, 'DAIRY'::character varying, 'GRAIN'::character varying, 'SEED'::character varying, 'OTHER'::character varying])::text[])))
);


ALTER TABLE public.ingredients OWNER TO postgres;

--
-- Name: ingredients_ingredient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.ingredients ALTER COLUMN ingredient_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ingredients_ingredient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: recipe_ingredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recipe_ingredients (
    id integer NOT NULL,
    ingredient_id integer NOT NULL,
    recipe_id integer NOT NULL,
    quantity character varying(255) NOT NULL
);


ALTER TABLE public.recipe_ingredients OWNER TO postgres;

--
-- Name: recipe_ingredients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.recipe_ingredients ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.recipe_ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: recipes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recipes (
    recipe_id integer NOT NULL,
    servings integer NOT NULL,
    recipe_name character varying(255) NOT NULL,
    instructions text NOT NULL
);


ALTER TABLE public.recipes OWNER TO postgres;

--
-- Name: recipes_recipe_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.recipes ALTER COLUMN recipe_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.recipes_recipe_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: ingredients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ingredients (ingredient_id, category, ingredient_name) FROM stdin;
1	VEGETABLE	sweet potatoes
2	VEGETABLE	spinach leaves
3	VEGETABLE	spring onions
4	VEGETABLE	mangetout
5	VEGETABLE	sweetcorn
6	SEED	sesame seeds
7	GRAIN	flour
8	OTHER	olive oil
9	OTHER	salt
10	OTHER	black pepper
\.


--
-- Data for Name: recipe_ingredients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recipe_ingredients (id, ingredient_id, recipe_id, quantity) FROM stdin;
1	1	1	500g
2	2	1	125g
3	3	1	4-5
4	4	1	125g
5	5	1	75g
6	6	1	3 tablespoons
7	7	1	4 tablespoons
8	8	1	
9	9	1	
10	10	1	
\.


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recipes (recipe_id, servings, recipe_name, instructions) FROM stdin;
1	4	Spinach and Sweet Potato Cakes	1. Bring a large saucepan of salted water to the boil, add the sweet potatoes and cook for about 20 minutes until tender. Drain the potatoes, then return them to the pan and place over low heat for 1 minute, stirring constantly to evaporate any excess moisture. Lightly mash the potatoes with a fork.\n\n2. Meanwhile, putthe spinach leaves in a colander and pour a pan of boiling water over. Rinse the spinach with cold water and squeeze the leaves dry. Stir the wilted spinach into the potatoes, then add the spring onions and mangetout. Season well with salt and pepper and set aside to cool.\n\n3. Use your hands to form the potato mixture into 12 little cakes. Mix the sesame seeds and flour together and sprinkle the mixture over the cakes.\n\n4. Heat a little oil in a large nonstick frying pan until hot but not smoking, and cook 4 of the cakes over a moderate heat for 4-5 minutes. Once a crisp crust has formed underneath, carefully turn them over and cook for a further 4-5 minutes on the other side. Keep warm while you repeat until all the cakes are made. You may need to add more oil to the pan to cook each batch.
\.


--
-- Name: ingredients_ingredient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ingredients_ingredient_id_seq', 10, true);


--
-- Name: recipe_ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recipe_ingredients_id_seq', 10, true);


--
-- Name: recipes_recipe_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recipes_recipe_id_seq', 1, true);


--
-- Name: ingredients ingredients_ingredient_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredients
    ADD CONSTRAINT ingredients_ingredient_name_key UNIQUE (ingredient_name);


--
-- Name: ingredients ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredients
    ADD CONSTRAINT ingredients_pkey PRIMARY KEY (ingredient_id);


--
-- Name: recipe_ingredients recipe_ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT recipe_ingredients_pkey PRIMARY KEY (id);


--
-- Name: recipes recipes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT recipes_pkey PRIMARY KEY (recipe_id);


--
-- Name: recipes recipes_recipe_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT recipes_recipe_name_key UNIQUE (recipe_name);


--
-- Name: recipe_ingredients fkcqlw8sor5ut10xsuj3jnttkc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT fkcqlw8sor5ut10xsuj3jnttkc FOREIGN KEY (recipe_id) REFERENCES public.recipes(recipe_id);


--
-- Name: recipe_ingredients fkgukrw6na9f61kb8djkkuvyxy8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT fkgukrw6na9f61kb8djkkuvyxy8 FOREIGN KEY (ingredient_id) REFERENCES public.ingredients(ingredient_id);


--
-- PostgreSQL database dump complete
--

