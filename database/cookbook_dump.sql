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

-- Drop tables and sequences if they exist
DROP TABLE IF EXISTS public.recipe_ingredients CASCADE;
DROP TABLE IF EXISTS public.recipes CASCADE;
DROP TABLE IF EXISTS public.ingredients CASCADE;

DROP SEQUENCE IF EXISTS public.recipe_ingredients_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.recipes_recipe_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.ingredients_ingredient_id_seq CASCADE;

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

CREATE SEQUENCE public.ingredients_ingredient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.ingredients_ingredient_id_seq OWNER TO postgres;

ALTER TABLE public.ingredients ALTER COLUMN ingredient_id SET DEFAULT nextval('public.ingredients_ingredient_id_seq');

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

CREATE SEQUENCE public.recipe_ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.recipe_ingredients_id_seq OWNER TO postgres;

ALTER TABLE public.recipe_ingredients ALTER COLUMN id SET DEFAULT nextval('public.recipe_ingredients_id_seq');

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

CREATE SEQUENCE public.recipes_recipe_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.recipes_recipe_id_seq OWNER TO postgres;

ALTER TABLE public.recipes ALTER COLUMN recipe_id SET DEFAULT nextval('public.recipes_recipe_id_seq');

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
13	VEGETABLE	Koriander
14	OTHER	allspice
15	OTHER	thyme
16	MEAT	chicken thighs
17	OTHER	sugar
18	OTHER	red wine
19	GRAIN	oats
20	DAIRY	milk
21	DAIRY	yogurt
22	OTHER	honey
23	FRUIT	fresh fruit of choice
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
30	10	13	1/2 teaspoon
31	14	13	1/2 teaspoon
32	15	13	2 teaspoons
33	8	13	3 tablespoons
34	16	13	750g
35	3	13	3 large
36	17	13	2 tablespoons
37	18	13	375 ml
38	19	14	50g
39	20	14	110-120ml
40	21	14	1 tablespoon
41	22	14	a drizzle
42	23	14	1 serving
\.

-- 
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recipes (recipe_id, servings, recipe_name, instructions) FROM stdin;
1	4	Spinach and Sweet Potato Cakes	1. Bring a large saucepan of salted water to the boil, add the sweet potatoes and cook for about 20 minutes until tender. Drain the potatoes, then return them to the pan and place over low heat for 1 minute, stirring constantly to evaporate any excess moisture. Lightly mash the potatoes with a fork.\n\n2. Meanwhile, putthe spinach leaves in a colander and pour a pan of boiling water over. Rinse the spinach with cold water and squeeze the leaves dry. Stir the wilted spinach into the potatoes, then add the spring onions and mangetout. Season well with salt and pepper and set aside to cool.\n\n3. Use your hands to form the potato mixture into 12 little cakes. Mix the sesame seeds and flour together and sprinkle the mixture over the cakes.\n\n4. Heat a little oil in a large nonstick frying pan until hot but not smoking, and cook 4 of the cakes over a moderate heat for 4-5 minutes. Once a crisp crust has formed underneath, carefully turn them over and cook for a further 4-5 minutes on the other side. Keep warm while you repeat until all the cakes are made. You may need to add more oil to the pan to cook each batch.
13	4	Sams Coney Stew	1. Mix the pepper with the allspice and chicken thighs.\n\n2. Heat the oil in a large, flameproof casserole and fry the meat in batches on all sides until browned. Drain the meat on the plate.\n\n3. Add the onions to the pan with the sugar and fry stir it for 15 minutes until caramelized. Stir in the garlic and cook for another minute.\n\n4. Cover with a lid and place in a preheated oven, 150 degrees Celcius for about 2 hours.
14	1	Travellers Overnight Oats	1. Combine all the ingredients for the base of the overnight oats in a container or glass jar and stir. Add one of the optional flavourings, stir to combine, and leave in the fridge overnight.\n\n2.Add extra fruit or yoghurt to serve, if desired.
\.

-- 
-- Set sequence values
--

SELECT pg_catalog.setval('public.ingredients_ingredient_id_seq', 23, true);
SELECT pg_catalog.setval('public.recipe_ingredients_id_seq', 42, true);
SELECT pg_catalog.setval('public.recipes_recipe_id_seq', 14, true);

-- 
-- Primary Key Constraints
--

ALTER TABLE ONLY public.ingredients
    ADD CONSTRAINT ingredients_pkey PRIMARY KEY (ingredient_id);

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT recipe_ingredients_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT recipes_pkey PRIMARY KEY (recipe_id);

-- 
-- Foreign Key Constraints
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT fkcqlw8sor5ut10xsuj3jnttkc FOREIGN KEY (recipe_id) REFERENCES public.recipes(recipe_id);

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT fkgukrw6na9f61kb8djkkuvyxy8 FOREIGN KEY (ingredient_id) REFERENCES public.ingredients(ingredient_id);

-- PostgreSQL database dump complete
