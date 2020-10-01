CREATE KEYSPACE mykeyspace WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy',   'datacenter1' : 1 };

use mykeyspace;

create table users(
	username text,
	importdate date,
	PRIMARY KEY (username)
);

create table accounts(
	username text,
	importdate date,
	id text,
	updatedate text,
	name text,
	product text,
	status text,
	type text,
	balance double,
	PRIMARY KEY ((username, importdate),id)
);

CREATE TYPE exchangerate (
currencyfrom text,
currencyto text,
rate double);

CREATE TYPE originalamount (
amount double,
currency text);

CREATE TYPE creditor (
maskedpan text,
name text);

CREATE TYPE debtor (
maskedpan text,
name text);

create table transactions(
	username text,
	importdate date,
	id text,
	accountid text,
	exchangerate frozen<exchangerate>,
	originalamount frozen<originalamount>,
	creditor frozen<creditor>,
	debtor frozen<debtor>,
	status text,
	currency text,
	amount double,
	updatedate text,
	description text,
	PRIMARY KEY ((username, importdate),id)
);

insert into users(username) values('a1');
insert into users(username) values('a2');
insert into users(username) values('a3');
insert into users(username) values('a4');
insert into users(username) values('a5');
insert into users(username) values('a6');
insert into users(username) values('a7');
insert into users(username) values('a8');
insert into users(username) values('a9');
insert into users(username) values('a10');
insert into users(username) values('a11');
insert into users(username) values('a12');
insert into users(username) values('a13');
insert into users(username) values('a14');
insert into users(username) values('a15');
insert into users(username) values('a16');
insert into users(username) values('a17');
insert into users(username) values('a18');
insert into users(username) values('a19');
insert into users(username) values('a20');
insert into users(username) values('a21');
insert into users(username) values('a22');
insert into users(username) values('a23');
insert into users(username) values('a24');
insert into users(username) values('a25');
insert into users(username) values('a26');
insert into users(username) values('a27');
insert into users(username) values('a28');
insert into users(username) values('a29');
insert into users(username) values('a30');
insert into users(username) values('a31');
insert into users(username) values('a32');
insert into users(username) values('a33');
insert into users(username) values('a34');
insert into users(username) values('a35');
insert into users(username) values('a36');
insert into users(username) values('a37');
insert into users(username) values('a38');
insert into users(username) values('a39');
insert into users(username) values('a40');
insert into users(username) values('a41');
insert into users(username) values('a42');
insert into users(username) values('a43');
insert into users(username) values('a44');
insert into users(username) values('a45');
insert into users(username) values('a46');
insert into users(username) values('a47');
insert into users(username) values('a48');
insert into users(username) values('a49');
insert into users(username) values('a50');
insert into users(username) values('a51');
insert into users(username) values('a52');
insert into users(username) values('a53');
insert into users(username) values('a54');
insert into users(username) values('a55');
insert into users(username) values('a56');
insert into users(username) values('a57');
insert into users(username) values('a58');
insert into users(username) values('a59');
insert into users(username) values('b1');
insert into users(username) values('b2');
insert into users(username) values('b3');
insert into users(username) values('b4');
insert into users(username) values('b5');
insert into users(username) values('b6');
insert into users(username) values('b7');
insert into users(username) values('b8');
insert into users(username) values('b9');
insert into users(username) values('b10');
insert into users(username) values('b11');
insert into users(username) values('b12');
insert into users(username) values('b13');
insert into users(username) values('b14');
insert into users(username) values('b15');
insert into users(username) values('b16');
insert into users(username) values('b17');
insert into users(username) values('b18');
insert into users(username) values('b19');
insert into users(username) values('b20');
insert into users(username) values('b21');
insert into users(username) values('b22');
insert into users(username) values('b23');
insert into users(username) values('b24');
insert into users(username) values('b25');
insert into users(username) values('b26');
insert into users(username) values('b27');
insert into users(username) values('b28');
insert into users(username) values('b29');
insert into users(username) values('b30');
insert into users(username) values('b31');
insert into users(username) values('b32');
insert into users(username) values('b33');
insert into users(username) values('b34');
insert into users(username) values('b35');
insert into users(username) values('b36');
insert into users(username) values('b37');
insert into users(username) values('b38');
insert into users(username) values('b39');
insert into users(username) values('b40');
insert into users(username) values('b41');
insert into users(username) values('b42');
insert into users(username) values('b43');
insert into users(username) values('b44');
insert into users(username) values('b45');
insert into users(username) values('b46');
insert into users(username) values('b47');
insert into users(username) values('b48');
insert into users(username) values('b49');
insert into users(username) values('b50');
insert into users(username) values('b51');
insert into users(username) values('b52');
insert into users(username) values('b53');
insert into users(username) values('b54');
insert into users(username) values('b55');
insert into users(username) values('b56');
insert into users(username) values('b57');
insert into users(username) values('b58');
insert into users(username) values('b59');
insert into users(username) values('c1');
insert into users(username) values('c2');
insert into users(username) values('c3');
insert into users(username) values('c4');
insert into users(username) values('c5');
insert into users(username) values('c6');
insert into users(username) values('c7');
insert into users(username) values('c8');
insert into users(username) values('c9');
insert into users(username) values('c10');
insert into users(username) values('c11');
insert into users(username) values('c12');
insert into users(username) values('c13');
insert into users(username) values('c14');
insert into users(username) values('c15');
insert into users(username) values('c16');
insert into users(username) values('c17');
insert into users(username) values('c18');
insert into users(username) values('c19');
insert into users(username) values('c20');
insert into users(username) values('c21');
insert into users(username) values('c22');
insert into users(username) values('c23');
insert into users(username) values('c24');
insert into users(username) values('c25');
insert into users(username) values('c26');
insert into users(username) values('c27');
insert into users(username) values('c28');
insert into users(username) values('c29');
insert into users(username) values('c30');
insert into users(username) values('c31');
insert into users(username) values('c32');
insert into users(username) values('c33');
insert into users(username) values('c34');
insert into users(username) values('c35');
insert into users(username) values('c36');
insert into users(username) values('c37');
insert into users(username) values('c38');
insert into users(username) values('c39');
insert into users(username) values('c40');
insert into users(username) values('c41');
insert into users(username) values('c42');
insert into users(username) values('c43');
insert into users(username) values('c44');
insert into users(username) values('c45');
insert into users(username) values('c46');
insert into users(username) values('c47');
insert into users(username) values('c48');
insert into users(username) values('c49');
insert into users(username) values('c50');
insert into users(username) values('c51');
insert into users(username) values('c52');
insert into users(username) values('c53');
insert into users(username) values('c54');
insert into users(username) values('c55');
insert into users(username) values('c56');
insert into users(username) values('c57');
insert into users(username) values('c58');
insert into users(username) values('c59');
