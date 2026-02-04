--
-- PostgreSQL database dump
--

\restrict 25hQ6ymbjaCl506QjAQhwAP113v8dGccr9HuXgyW4X3RxMhSsGf7fGgK2U9iqvz

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2026-02-04 11:29:45

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
-- TOC entry 224 (class 1259 OID 16549)
-- Name: appointment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appointment (
    id bigint NOT NULL,
    pet_id bigint NOT NULL,
    doctor_id bigint NOT NULL,
    starts_at timestamp(6) with time zone NOT NULL,
    ends_at timestamp(6) with time zone NOT NULL,
    reason character varying(500),
    status character varying(50)
);


ALTER TABLE public.appointment OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16548)
-- Name: appointment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.appointment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.appointment_id_seq OWNER TO postgres;

--
-- TOC entry 4934 (class 0 OID 0)
-- Dependencies: 223
-- Name: appointment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.appointment_id_seq OWNED BY public.appointment.id;


--
-- TOC entry 220 (class 1259 OID 16526)
-- Name: doctor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor (
    id bigint NOT NULL,
    full_name character varying(255) NOT NULL,
    specialization character varying(255) NOT NULL,
    phone character varying(50) NOT NULL
);


ALTER TABLE public.doctor OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16525)
-- Name: doctor_doctor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.doctor_doctor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.doctor_doctor_id_seq OWNER TO postgres;

--
-- TOC entry 4935 (class 0 OID 0)
-- Dependencies: 219
-- Name: doctor_doctor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.doctor_doctor_id_seq OWNED BY public.doctor.id;


--
-- TOC entry 218 (class 1259 OID 16515)
-- Name: owner; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.owner (
    id bigint NOT NULL,
    full_name character varying(255) NOT NULL,
    phone character varying(50) NOT NULL,
    email character varying(255),
    address character varying(255)
);


ALTER TABLE public.owner OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16514)
-- Name: owner_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.owner_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.owner_id_seq OWNER TO postgres;

--
-- TOC entry 4936 (class 0 OID 0)
-- Dependencies: 217
-- Name: owner_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.owner_id_seq OWNED BY public.owner.id;


--
-- TOC entry 222 (class 1259 OID 16535)
-- Name: pet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pet (
    id bigint NOT NULL,
    owner_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    species character varying(255) NOT NULL,
    breed character varying(255),
    gender character varying(20)
);


ALTER TABLE public.pet OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16534)
-- Name: pet_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pet_id_seq OWNER TO postgres;

--
-- TOC entry 4937 (class 0 OID 0)
-- Dependencies: 221
-- Name: pet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pet_id_seq OWNED BY public.pet.id;


--
-- TOC entry 4760 (class 2604 OID 16552)
-- Name: appointment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment ALTER COLUMN id SET DEFAULT nextval('public.appointment_id_seq'::regclass);


--
-- TOC entry 4758 (class 2604 OID 16529)
-- Name: doctor id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor ALTER COLUMN id SET DEFAULT nextval('public.doctor_doctor_id_seq'::regclass);


--
-- TOC entry 4757 (class 2604 OID 16518)
-- Name: owner id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owner ALTER COLUMN id SET DEFAULT nextval('public.owner_id_seq'::regclass);


--
-- TOC entry 4759 (class 2604 OID 16538)
-- Name: pet id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet ALTER COLUMN id SET DEFAULT nextval('public.pet_id_seq'::regclass);


--
-- TOC entry 4928 (class 0 OID 16549)
-- Dependencies: 224
-- Data for Name: appointment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.appointment (id, pet_id, doctor_id, starts_at, ends_at, reason, status) FROM stdin;
8	2	4	2026-02-04 17:30:00+03	2026-02-04 18:00:00+03	CheckUp	cancelled
6	3	4	2026-02-04 17:00:00+03	2026-02-04 18:00:00+03	Bad view of cat	cancelled
\.


--
-- TOC entry 4924 (class 0 OID 16526)
-- Dependencies: 220
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctor (id, full_name, specialization, phone) FROM stdin;
4	Petrov Petr Ilich	Therapist	987654321
\.


--
-- TOC entry 4922 (class 0 OID 16515)
-- Dependencies: 218
-- Data for Name: owner; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.owner (id, full_name, phone, email, address) FROM stdin;
3	Ivanov Ivan Ivanovich	123456789	example@email.com	Voronezh, Main Street
\.


--
-- TOC entry 4926 (class 0 OID 16535)
-- Dependencies: 222
-- Data for Name: pet; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pet (id, owner_id, name, species, breed, gender) FROM stdin;
2	3	Jordan	Dog	Husky	male
3	3	May	Cat	Mainkoon	female
\.


--
-- TOC entry 4938 (class 0 OID 0)
-- Dependencies: 223
-- Name: appointment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.appointment_id_seq', 8, true);


--
-- TOC entry 4939 (class 0 OID 0)
-- Dependencies: 219
-- Name: doctor_doctor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.doctor_doctor_id_seq', 5, true);


--
-- TOC entry 4940 (class 0 OID 0)
-- Dependencies: 217
-- Name: owner_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.owner_id_seq', 5, true);


--
-- TOC entry 4941 (class 0 OID 0)
-- Dependencies: 221
-- Name: pet_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pet_id_seq', 4, true);


--
-- TOC entry 4772 (class 2606 OID 16556)
-- Name: appointment appointment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT appointment_pkey PRIMARY KEY (id);


--
-- TOC entry 4766 (class 2606 OID 16568)
-- Name: doctor doctor_phone_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_phone_key UNIQUE (phone);


--
-- TOC entry 4768 (class 2606 OID 16533)
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (id);


--
-- TOC entry 4762 (class 2606 OID 16522)
-- Name: owner owner_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owner
    ADD CONSTRAINT owner_pkey PRIMARY KEY (id);


--
-- TOC entry 4770 (class 2606 OID 16542)
-- Name: pet pet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet
    ADD CONSTRAINT pet_pkey PRIMARY KEY (id);


--
-- TOC entry 4764 (class 2606 OID 16524)
-- Name: owner phone; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owner
    ADD CONSTRAINT phone UNIQUE (phone);


--
-- TOC entry 4774 (class 2606 OID 16562)
-- Name: appointment appointment_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT appointment_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.doctor(id);


--
-- TOC entry 4775 (class 2606 OID 16557)
-- Name: appointment appointment_pet_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT appointment_pet_id_fkey FOREIGN KEY (pet_id) REFERENCES public.pet(id);


--
-- TOC entry 4773 (class 2606 OID 16543)
-- Name: pet owner_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet
    ADD CONSTRAINT owner_id FOREIGN KEY (owner_id) REFERENCES public.owner(id);


-- Completed on 2026-02-04 11:29:46

--
-- PostgreSQL database dump complete
--

\unrestrict 25hQ6ymbjaCl506QjAQhwAP113v8dGccr9HuXgyW4X3RxMhSsGf7fGgK2U9iqvz

