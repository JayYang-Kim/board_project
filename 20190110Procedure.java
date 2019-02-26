//******************************************
-- 오라클 설치
  오라클 사이트에서 다음의 프로그램중 하나를 다운로드하고 설치
  http://www.oracle.com/technetwork/indexes/downloads/index.html#database

    Database 11g Express Edition 
    Database 11g Enterprise/Standard Editions
	
//******************************************
#실습용 sky 사용자 추가

cmd>sqlplus sys/"암호"  as sysdba

#사용자 확인(SYS 계정)
SELECT * FROM ALL_USERS;

#sky 사용자가 존재하는 경우 사용자 제거(SYS 계정)
DROP USER sky CASCADE; 

#sky 사용자 추가(SYS 계정)
CREATE USER sky IDENTIFIED BY "java$!";

#sky 사용자 CONN, 테이블작성, 테이블 스페이스 권한 설정(SYS 계정)
GRANT CONNECT, RESOURCE TO sky;

#sky 사용자 DEFAULT 테이블스페이스를 USERS로 변경(SYS 계정)
ALTER USER sky DEFAULT TABLESPACE USERS;

#sky 사용자 TEMPORARY 테이블스페이스를 TEMP 변경(SYS 계정)
ALTER USER sky TEMPORARY TABLESPACE TEMP;

 [주의] 사용자를 추가하고 DEFAULT 테이블스페이스를 지정하지 않으면 기본으로 SYSTEM 테이블스페이스(데이터 운영에 필요한 정보등을 저장)를 사용하며, SYSTEM 테이블스페이스는 휴지통이 없다.


//******************************************
#sky 사용자로 CONNECT 후에 인사테이블 실습자료 복사하여 붙여넣기한다.
  sql>CONN sky/"java$!"

  인사테이블 실습자료 복사하여 붙여넣기


//******************************************
#사용자 암호를 변경 할 경우(자기자신 또는 SYS 계정)
ALTER USER sky IDENTIFIED BY "암호";

#LOCK이 설정된 경우 LOCK 해제(SYS 계정)
ALTER USER sky ACCOUNT UNLOCK;

#제약 조건이 있어도 테이블 삭제할 경우
DROP TABLE 테이블명 CASCADE CONSTRAINTS PURGE;


//******************************************
#사용자 제거(CASCADE 옵션으로 모든 객체도 삭제) : SYS 계정
DROP USER 사용자명 CASCADE; 

SELECT * FROM all_users;




//*****************************************
#오라클 데이터베이스 및 SID 확인(SYS 계정)

#SID 란?
    1. System Identifier 의 약자로 데이터베이스를 식별함에 있어 고유한 아이디
    2. 데이타베이스가 하나만으로 구성 되어 있다면 데이타베이스명이 SID가 된다. 하지만 RAC 로 구성하여 데이타베이스 두개가 동시 가동되는 경우라면 SID 가 서로 다를 수 있기 때문에 중복 확인해야 한다.
       -- DB연동을 위하여 필요한 naming 이다.

       DBMS 서버를 기동하기 위해서는 DB서버가 기동하는 서버의 IP 그리고 DB서버가 접속을 받아들이기 위한 프로토콜에 대한 정의가 필요하다. 오라클의 경우 인스턴스가 서버 역할을 하는 DBMS 프로세스인데, 인스턴스가 기동할때 SID를 필요로 한다. 즉 SID는 인스턴스의 이름이다.

       그리고 SID정보는 환경변수와, LISTENER.ORA라는 파일에서 정의 된다. listener.ora 파일을 열어보면
       (SID_NAME = xe)
       라고 기술된 부분이 있는데 이것이 SID이다.

#오라클 데이타베이스명을 확인(XE)
	SQL>SELECT NAME, DB_UNIQUE_NAME FROM v$database;

#오라클 SID를 확인(xe)
	SQL>SELECT instance FROM v$thread;

#SQLPLUS 실행(일반사용자)
	CMD>sqlplus 사용자명/"암호"

#테이블 목록 확인
	SQL>SELECT * FROM TAB;

#테이블 구조 확인
	SQL>DESC[RIBE] 테이블명;
	SQL>SELECT * FROM COL WHERE tname='테이블명';
	   -- 테이블명은 반드시 대문자로

#iSQL*Plus 실행(일반 사용자)
	http://컴퓨터명:5560/isqlplus/
	
#em 실행(관리자 영역)
	http://컴퓨터명:1158/em

	tnsnames.ora 에 정의된 connect_identifier 으로 접속
	cmd>sqlplus 사용자명/"암호"@orcl
		-- orcl : tnsnames.ora 에 정의된 connect_identifier

#sql 에서 cmd 창으로 잠시 빠져 나가야 하는 경우
	sql>HOST
	cmd>EXIT    -- SQL 로 복귀


#sqlplus 에서 *.sql 파일 실행
	파일이 존재하는 경로에서 sqlplus를 실행하고,
	"@파일명" 명령을 실행하면 된다.
	sql>@ex.sql
	경로를 지정하여 실행
	sql>@d:\sql\ex.sql

#sqlplus 에서 sql 작업 내용 자동 저장하기
	sql>spool d:\sql\test.txt
	sql>SELECT * FROM insa;   -- d:\sql\test.txt 파일 생성
	sql>spool off  -- 스풀 정지

#오라클 쿼리 실행 시간 출력
	실행시간 시작 : SET TIMING ON
	실행시간 종료 : SET TIMING OFF

	sql>SET TIMING ON
	sql>SELECT * FROM tab;

	1. 연산자
	-- 산술연산자
	   + : 덧셈  - : 뺄셈  * : 곱셈  / : 나눗셈  ( ) : 괄호

	-- 비교연산자
	   = : 같다.  > : 크다.  >= : 크거나 같다.
	  < : 적다.  <= : 작거나 같다.	 <> : 같지 않다.   != : 같지 않다.

	-- 논리 연산자
	  AND : 논리 곱  OR : 논리 합  NOT : 부정

	-- SQL 연산자
	 IN(값, 값, ...) : 피연산자가 식 목록 중 하나와 동일한 경우 TRUE
	 BETWEEN ~ AND: 피연산자가 범위 안에 있는 경우 TRUE
	 LIKE : 피연산자가 패턴과 일치하는 경우 TRUE

	-- 문자열 연결 연산자
	   || : 문자열 연결

	   ' 를 출력할 경우에는 '을 두번 쓴다.
	   SELECT 'test''s' FROM insa;
	   SELECT name || '''' FROM insa;

#SELECT
#형식 : SELECT 필드명, 필드명 ..... FROM 테이블
#실행 순서
     FROM 절 -> WHERE 절 -> ROWNUM 할당 -> GROUP BY 절 -> HAVING -> SELECT 절 -> ORDER BY 절

#필드명 변경(AS는 생략가능. 별명에서 띄어쓰기등을 하는 경우에는 "" 안에 기술)
   형식 : SELECT 필드명 AS 별명, 필드명 AS 별명 FROM 테이블


//*****************************************
#조건 검색
   형식 : SELECT 필드명, 필드명 ..., 필드명 FROM 테이블 WHERE 조건


//*****************************************
#테이블 구조 확인
	DESC 테이블명;
	SELECT * FROM col WHERE tname='테이블명';

------------------------------------------
#테이블 목록 확인
	SELECT * FROM tab;

#테이블 구조 확인
	DESC  insa;
	SELECT * FROM col WHERE tname='INSA';

#전체출력
	SELECT * FROM insa;

#name, tel, buseo 컬럼
	SELECT name, tel, buseo
	FROM insa;

#컬럼명 변경
	SELECT name AS 이름, tel AS 전화, buseo AS 부서명
	FROM insa;

#AS 생략가능
	SELECT name 이름, tel 전화, buseo 부서명
	FROM insa;

	SELECT name 이름, tel 전화, buseo "부서 명"
	FROM insa;

#연산
	SELECT name, basicpay FROM insa;

	SELECT name||'님', basicpay/10000 FROM insa;

	SELECT name||'님' AS name, basicpay/10000 pay FROM insa;

	-- 조건검색
	-- city가 서울인 사람의 name, city, basicpay 출력
	SELECT name, city, basicpay
	FROM insa
	WHERE city='서울';

	-- basicpay+sudang 가 300만원 이상인 name, basicpay, sudang, basicpay+sudang 출력
	SELECT name, basicpay, sudang, basicpay+sudang pay
	FROM insa
	WHERE basicpay+sudang>=2500000;

	SELECT name, basicpay, sudang, basicpay+sudang pay
	FROM insa
	WHERE pay>=2500000;   -- (X)


//*****************************************
-- DUAL
  - DUAL 테이블은 데이터 딕셔너리와 함께 Oracle에 의해 자동으로 생성되는 테이블로 사용자 SYS의 스키마에 존재하며, 모든 사용자가 사용 가능하다.


//*****************************************
#단일행(single row, 스칼라) 함수

#문자함수

#SUBSTR (char, m [,n]) : 문자열 추출. 인덱스는 1부터 시작한다.
	자바의 substring같은 기능
	select substr('seoul korea',7,3) from insa; //이렇게 쓰면 60개 나옴 //레코드가 60개 잇으므로

#LENGTH (column | expression) : 문자열 길이
#LENGTHB (column | expression) : 문자열 바이트수
   -- 11g에서는 한글은 UTF-8로 처리(한글 1자는 3byte)
	select length('대한민국') from dual; --4;
	select lengthB('대한민국') from dual; --12;
	대한민국을 저장하려면 최소 12칸 짜리 방을 설계해야한다.

#INSTR (column | expression, 'string' [,m] [,n])
   문자의 위치를 반환한다. m값은 시작위치고, n값은 발생 횟수이며 m과 n의 기본 값은 1이다.
   문자가 없으면 0을 반환한다.
	like 쓰다 걸리면 딱밤 맞음.... 하지만 알아는둬야함

#LPAD (column | expression, n, ['string']), RPAD (column | expression, n, ['string'])
   expression의 문자열을 제외한 공간에 문자열을 왼쪽(오른쪽)에 채운다.

#REPLACE (text, search_string [, replacement_string])
   문자열 치환
	select replace('seoul korea', 'seoul','busan') from dual;
	전체 문자열,'치환될 문자열', '치환할 문자열'

#TRANSLATE ('char','from_string','to_string')
   char 내에 포함된 문자중 from_string에 지정한 모든 각각의 문자를 to_string 문자로 각각 변경한다.
		언뜻 보면 replace와 유사해보이나
		select TRANSLATE('ababccc', 'c', 'd') from dual; -- ababddd
		0~9까지는 9로 바꾸고 영문자는 *로 바꿔라
		select TRANSLATE('2dk5erw6', '0123456789abcdefghijklmnopqrstuvwxyz','9999999999**************************') from dual; -- 9**9***9
		거지같네...
		숫자만 나오고 영어는 사라지게 하기
		replace는 문자열을 문자열로 바꾸기
		translate는 해당위치의 문자를 다음 위치의 문자로 바꾸기
		select TRANSLATE('2dk5erw6', '0123456789abcdefghijklmnopqrstuvwxyz','0123456789') from dual; -- 9**9***9

#RTRIM(char [,set]), LTRIM(char [,set])
   TRIM (leading | trailing | both trim_character FROM trim_source)
   공백을 제거하거나 오른쪽(왼쪽)의 문자열 제거

#UPPER(char) : 영문자 문자열을 모두 대문자로 변환
#LOWER(char) : 영문자 문자열을 모두 소문자로 변환
#ASCII(char) : 주어진 char의 첫 글자의 아스키 값을 반환
#CHR(n) : 입력된 수의 바이너리 코드에 해당하는 문자를 반환
#INITCAP(char) : 입력 문자열 중에서 각 단어의 첫 문자를 대문자로 나머지 문자는 소문자로 변환

#숫자함수

#MOD(m, n) : m을 n으로 나눈 나머지
	select MOD(11,4) from dual; -- 3
	select name, ssn from insa where mod(substr(ssn,8,1),2)=0; --뒷자리 첫자리 가져와 짝수인지 홀수인지 구분

#ROUND(n [,m]) : 반올림
	select ROUND(15.193,1) from dual; --15.2 --소수점의 자리수를 한자리만 찍어라 두번째 자리에서 반올림
	select ROUND(15.193) from dual; --15
	select ROUND(15.193,-1) from dual; --20 소수점을 0으로 잡고 이를 기준으로 일의자리에서 반올림하고 싶으면 -1

#TRUNC(n [,m]) : 절삭 --은행권에선 무조건 절삭
	select TRUNC(15.79,1) from dual; --15.7 --소수점의 자리수를 한자리만 찍어라 두번째자리에서 내림
	select TRUNC(15.79,-1) from dual; --10

#암시적 형 변환
	- 값을 할당할 때 오라클 서버는 다음과 같이 자동으로 값을 변환할 수 있다.

	  VARCHAR2, CHAR → NUMBER
	  VARCHAR2, CHAR → DATE
	  NUMBER → VARCHAR2
	  DATE → VARCHAR2

#명시적 형변환
	TO_CHAR(label [, fmt]) : MLSLABEL datatype을 VARCHAR2 타입으로 변환
    TO_CHAR(n [, fmt [, 'nlsparams'] ]) : 숫자를 문자로 변환(VARCHAR2 타입)
    TO_CHAR(d [, fmt [, 'nlsparams'] ]) : 날짜를 문자로 변환(VARCHAR2 타입)

	   YYYY : 4자리 연도로 표시 
	   YY : 끝의 2자리 연도로 표시(무조건 시스템 상의 년도)
	   RR : 
		 - 현재년도가 0~49사이에 있고 표현할 연도가 0~49이면 현재 세기를 적용
		 - 현재년도가 0~49사이에 있고 표현할 연도가 50~99이면 (현재세기-1)를 적용
		 - 현재년도가 50~99사이에 있고 표현할 연도가 0~49이면 (현재세기+1)를 적용
		 - 현재년도가 50~99사이에 있고 표현할 연도가 50~99이면 현재 세기를 적용
		 - 현재 : 2015년, 연도 : 70 -> 1970년
		 - 현재 : 2015년, 연도 : 08 -> 2008년
		YY와 RR의 차이 : RR은 현재 시스템의 날짜를 기준으로 진행 따라서 YY를 쓰지말고 RR을 쓰자
		YYYY나 RR을 사용하거라~
	   YEAR : 연도를 알파벳으로 표시  //한국에선 한글로 표시
	   MM : 달을 숫자로 표시 
	   MON : 달을 알파벳 약어 로표시  //한국에선 한글로 표시
	   MONTH : 달을 알파벳으로 표시 //한국에선 한글로 표시
	   DD : 일자를 숫자로 표시 
	   DAY : 일에 해당하는 요일 
	   DY : 일에 해당하는 요일의 약어
	   D : 일에 해당하는 요일을 숫자로 표시(1~7)
	   HH : 12시간으로 표시(1-12) 
	   HH24 : 24시간으로 표시(0-23) 
	   MI : 분을 표시 
	   SS : 초를 표시 
	   AM(또는 PM) : 오전인지 오후인지를 표시 

	   9 : 숫자의 출력 자릿수를 지정. 값이 지정한 자릿수보다 작으면 공백으로 채워 표시. '999'는 숫자를 3자리로 출력
		   만약 자릿수가 부족하면 #으로 출력
	   0 : 자릿수가 비면 0을 채움. 123을 '0999'로 출력하면 0123출력
	   $ : 숫자앞에 달러를 붙인다.
	   L : 국가별 화페를 출력.
	   . : 소수점(한 위치에 마침표 표기)
		   TO_CHAR(1234,'99999.99') -> 1234.00 
	   , : 천단위로 ,을 출력(명시한 위치에 컴마 표기).
		   TO_CHAR(1234,'99,999') -> 1,234
	   PR : 음수를 괄호(<>)로 묶음
	   MI : 음수인 경우 뒤에 '-' 기호를 표시하며 양수인 경우 뒤에 공백
	   V : 10을 V 뒤의 수만큼 곱함 
	   EEEE : 지수 표기법으로 표시 

	--세자리마다 콤마 붙이고 통화기호 붙이기 =>자바가 훨씬 좋음 편하긴 오라클이 편하나 성능저하가 심해짐
	select to_char(basicpay,'9,999,999'),to_char(basicpay,'L9,999,999'), to_char(basicpay,'999,999') from insa;
	-- 세번째껀 부족하면 ####### 나옴

	Select to_char(12345,'0,999,999') from dual;
	Select to_char(12345,'0,999,999') from dual; --남는 자리수는 알아서 0으로 채워쥼
	Select to_char(123.45,'990.0') from dual; --남는 자리수는 알아서 0으로 채워쥼 자동으로 반올림된다.
	elect to_char(123.45,'990.00') from dual; --남는 자리수는 알아서 0으로 채워쥼 반올림 x 해당 자리만큼 나옴
	Select to_char(123.45,'999.9') from dual; --남는 자리수는 알아서 0으로 채워쥼 자동으로 반올림된다.
	Select to_char(123,'999.9') from dual; --남는 자리수는 알아서 0으로 채워쥼 소수점이 자동 출력.

	select to_char(1234,'9999MI') from dual;  --'1234' 형태로 출력
	select to_char(-1234,'9999MI') from dual; --'1234-'형태로 출력
	select to_char(-1234,'9999PR') from dual; --<1234> 형태로 출력
	select to_char(1234.345,'9.999EEEE') from dual;  --1.234E+03 지수 형태로 출력 9.999형태로 출력하겠다.
	select to_char(1234.345,'99999V9999') from dual;  --  12343450
	9를 쓰나 0을 쓰나 크게 차이는 없졍

#통화기호, 날짜 등 출력 형식 변경
	SELECT parameter, value FROM NLS_SESSION_PARAMETERS;  -- 확인

	ALTER SESSION SET NLS_LANGUAGE = 'KOREAN';
	ALTER SESSION SET NLS_CURRENCY = '\';
	ALTER SESSION SET NLS_DATE_LANGUAGE = 'KOREAN';

#날짜형식 변경(기본:RR/MM/DD)
	ALTER SESSION SET NLS_DATE_FORMAT='YYYY-MM-DD';

#TO_NUMBER(char [,fmt [, 'nlsparams'] ]) : 문자를 숫자로 변환
	select '12'+20 from dual;
	select '12'+20 from dual; -- 32 --오라클에서 알아서 변경해줌
	--그럼 어떤 경우에 사용하는가
	--select '1,200'+20 from dual; --에러
	select to_number('1,200','9,999')+20 from dual; --형식 변환을 통해서 해결 --하지만 자바가 훨씬 도움된다. 속도향상에

#TO_DATE(char [, fmt [, 'nlsparams'] ]) : 문자를 날짜로 변환
	-월일년 을 년월일로 바꾸는 등의 경우에서 사용한다.

		select TO_char(sysdate, 'DD-MON-RR') from dual; --02-1월 -19 --현재 출력이 이런식으로 되고 있기때문에
		--select TO_DATE('01-JUL-08', 'DD-MON-RR') from dual; --이렇게 하면 먹지 않는다. 왜? 날짜 형식이 다르므로, 날짜 형식이 미국식인 경우만 가능하다
		select TO_DATE('01-10-90', 'MM-DD-RR') from dual; --90/01/10 //sysdate의 기본형식이 이런형태라 순서대로 나옴
		--select name, ssn from insa;

		--select name, substr(ssn,1,6) birth from insa;

		--select name, to_date(substr(ssn,1,6),'RRMMDD') birth from insa;
		--위와 같다
		--select name, to_date(substr(ssn,1,6),'RRMMDD') birth from insa;


--select name, to_char(to_date(substr(ssn,1,6)),'YYYY-MM-DD') birth from insa; --아래 형태로 출력한다.

	select to_char(Sysdate, 'MON DD DAY') from dual; --한국형태로 출력된다. 이를 미국식으로 보려면
	select to_char(Sysdate, 'MON DD DAY','NLS_DATE_LANGUAGE=American') from dual; --이와 같이 출력하면된다.

#ASCIISTR(string) : string의 아스키 문자로 반환. non-ASCII 문자는 UTF-16 code로 반환. 
	select asciistr('kor') from dual; --kor
	select asciistr('대한') from dual; --\B300\D55C 영문자가 아닌 값들은 아스키코드 값으로 출력한다. \B300(대)\D55C(한)

//-------------------------------------------------------------------------
#날짜 및 날짜함수

#날짜에 산술 연산을 사용하는 경우
	  날짜 + 숫자 → 날짜 : 날짜에 일수를 더하여 날짜 계산
	  날짜 - 숫자  → 날짜 : 날짜에 일수를 감하여 날짜 계산
	  날짜 + 숫자/24  → 날짜 : 날짜에 시간을 더하여 날짜 계산
	  날짜 - 날짜  → 일수 : 날짜에 날짜를 감하여 일수 계산

#SYSDATE : 시스템에 저장된 현재 날짜를 반환

#CURRENT_DATE : 현재 session의 날짜 정보를 반환
	select current_date "today", add_months(current_date,1) "nextMonth" from dual;

#ADD_MONTHS(d, n) : 해당 날짜에 n만큼의 달수를 더한다.
   기준이 되는 날짜가 '말일'일 경우는, 계산된 값도 해당 월의 말일을 반환한다.
	select add_months(To_date('2019-03-31','YYYY-MM-DD'),1) from dual; =>20190430
	1월 31 -> 2월 28

	6개월 후
	2014-03-30,2014-03-31 => 2014-09-30; 9월의 말일이 30일이므로 둘다 9월 30일로 출력된다.

	지난 달
	-1을 대입하면 된다.

#LAST_DAY(d) : 정의된 날짜의 달에서 마지막일이 몇일인지 돌려준다.
	select last_day(sysdate) from dual;

#MONTHS_BETWEEN(d1, d2) : 정의된 두 날짜사이의 차이(d1 - d2)를 월로 돌려준다.
	주민번호의 생일을 기준으로 만나이를 구할수 있다.
	trunc(months_between(sysdate,substr(ssn,1,6))/12)

#ROUND(d [,fmt]) : 정해진 날짜를 fmt를 기준으로 반올림
	--'YEAR' 년도 기준은 7월 1일부터 반올림
	--'MONTH' 월 기준은 16일을 기준으로 반올림
	select round(to_date('070710'),'YEAR') from dual; --2008-01-01
	select round(to_date('070630'),'YEAR') from dual; --2007-01-01

	select round(to_date('070720'),'MONTH') from dual; --2007-08-01
	select round(to_date('070715'),'MONTH') from dual; --2007-07-01

#TRUNC(d,[fmt]) : 정해진 날짜를 fmt를 기준으로 내림
	select trunc(to_date('070710'),'YEAR') from dual; --07/01/01
	select name, ibsadate, trunc(ibsadate, 'month') from insa; 다 해당월 1일로 내림

#NEXT_DAY(d, char)
   명시된 요일(char)이 돌아오는 날짜를 계산한다. 요일은 숫자로 표현 가능(SUNDAY : 1, MONDAY : 2, ...)
   select sysdate, NEXT_DAY(sysdate, '월요일') from dual; --오늘 날짜를 기준으로 가장 가까운 월요일 찾기
   select sysdate, NEXT_DAY(sysdate, 2) from dual; --오늘 날짜를 기준으로 가장 가까운 월요일 찾기

#EXTRACT ({year|month|day|hour|minute|second|
         timezone_hour|timezone_minute|
         timezone_region|timezone_abbr} 
     FROM {datetime_value_expr|interval_value_rxpr})
	 특정 날짜/시간 값이나 날짜 값을 가진 표현식으로 부터 원하는 날짜 영역을 추출하여 출력한다.
	select extract(year from sysdate) from dual;
	 ==select to_char(sysdate,'YYYY') from dual;
 
	select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') from dual; --시간 출력
	오늘 0시 0분 0초 가져오기
	select to_char(trunc(sysdate,'DD'),'YYYY-MM-DD HH24:MI:SS') from dual;
	내일 0시 0분 0초 가져오기
	select to_char(trunc(sysdate+1,'DD'),'YYYY-MM-DD HH24:MI:SS') from dual;

#TIMESTAMP형
   초단위 이하의 밀리세컨드를 처리하기 위해 사용
   DATE형은 초까지만 처리

   - 밀리세컨드를 3자리로 표현할 경우는 FF3, 4자리로 표현할 경우는 FF4로 나타냄.
   - 현재시간을 입력 할 경우 DATE형은 SYSDATE, TIMESTAMP는 SYSTIMESTAMP를 사용
select to_char(SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.FF3') from dual;

#INTERVAL 사용방법
		select sysdate+1 from dual; --sysdate에 하루를 더해줘라
		1년을 더해보자
		select SYSDATE + (INTERVAL '(+-)1' YEAR) from dual;
		1개월을 더해보자
		select SYSDATE + (INTERVAL '1' MONTH) from dual;
		1일을 더해보자
		select SYSDATE + (INTERVAL '1' DAY) from dual;
		1시간을 더해보자
		select to_char(SYSDATE + (INTERVAL '1' HOUR),'YYYY-MM-DD HH24-MI-SS') from dual;
		2시간 십분을 더해보자
		select to_char(SYSDATE + (INTERVAL '02:10' HOUR TO MINUTE),'YYYY-MM-DD HH24-MI-SS') from dual;
		1분 30초 더해보자
		select to_char(SYSDATE + (INTERVAL '01:30' MINUTE TO SECOND),'YYYY-MM-DD HH24-MI-SS') from dual;
		3년 전
		select SYSDATE + (INTERVAL '-3' YEAR) from dual;
		---------------------------------------------
		'2010-10-10 10:10:10'	'2010-10-02 09:09:09'

		SELECT (TO_DATE('2010-10-10 10:10:10','YYYY-MM-DD HH24:MI:SS')-TO_DATE('2010-10-02 09:09:09','YYYY-MM-DD HH24:MI:SS'))*24*60*60 차이 FROM DUAL;

#NULL : 값이 존재하지 않은 상태. 오라클에서는 ''(문자길이가 0 인경우)도 널이다.
		IS NULL : NULL 이면 참
		IS NOT NULL : NULL 이 아니면 참
		NULL을 비교할때는 널을 사용할수 없다.
		SELECT NAME, CITY, BASICPAY, IBSADATE, TEL FROM INSA WHERE TEL IS NULL;
		SELECT NAME, CITY, BASICPAY, IBSADATE, TEL FROM INSA WHERE TEL IS NOT NULL;

#NULL 관련 함수
	   NVL(expr1, expr2) : expr1이 Null 이면 expr2 반환하고 Null이 아니면 expr1 반환(중요)
	   SELECT NVL(TEL,'전화가 없음'), NAME FROM INSA; --전화번호가 존재하면 전화번호를 리턴하고 아니면 전화가 없음을 리턴한다.
	   --MVL 사용 경우 
	   10+NULL => NULL
	   SELECT 10+NVL(NULL,0) FROM DUAL;
	   
	   NVL2(expr1, expr2, expr3) : expr1이 null이 아니면 expr2를 반환하고, null이면 expr3를 반환
	   SELECT NVL2(TEL,'전화가 있음','전화가 없음'), NAME FROM INSA;

	   NULLIF(expr1, expr2) : 두 표현식을 비교해서 같으면 null 반환하고, 같지 않으면 expr1을 반환
	   SELECT NULLIF(1,1) FROM DUAL; =>NULL
	   SELECT NULLIF(1,0) FROM DUAL; => 1
		  
	   COALESCE(expr1, expr2, ……, exprn) : 표현식 목록에서 첫 번째로 null이 아닌 것을 반환한다. 즉, expr1이 널이 아니면 expr1을 반환하며, expr1이 널이고 expr2가 널이 아니면 expr2를 반환한다.
	   SELECT COALESCE(NULL,3,NULL,5) FROM DUAL; =>3
	   LNNVL(조건) : 조건이 거짓이거나 값이 존재하지 않으면 TRUE를 리턴하며, 조건이 거짓이면 FALSE를 리턴 한다. SELECT 리스트에서는 사용할 수 없으며 WHERE 절에서만 사용 가능 하다.

#ORDER BY : 정렬
	   - ASC : 오름차순, DESC : 내림차순
	   - 첫번째 행 부터 NULL 값 정렬 
		 ORDER BY 컬럼명 NULLS FIRST; 
	   - NULL 값을 마지막 행으로 정렬 
		 ORDER BY 컬럼명 NULLS LAST; 
	   - ORDER BY 구문 활용시 단순 칼럼명으로 정렬하는것이 아니라 특정칼럼의 특정값을 먼저 오게 하는것처럼 정렬순서를 변경하고 싶은경우 ORDER BY 구문과 DECODE 혹은 CASE WHEN 구문을 함께 활용하면 된다.
		SELECT NAME, CITY, BUSEO, JIKWI, BASICPAY, SUDANG, BASICPAY+SUDANG PAY FROM INSA ORDER BY PAY DESC; --ORDER BY절에서는 AS로 준 별명 사용가능

#DISTINCT : 중복 배제(반대 : ALL -- 기본)
	SELECT buseo FROM insa;
	SELECT DISTINCT BUSEO FROM INSA;
	SELECT DISTINCT BUSEO, JIKWI, SUBSTR(NAME,1,1) FROM INSA ORDER BY BUSEO;

#IN
    1. IN(값, 값, ...) : 피연산자가 식 목록 중 하나와 동일한 경우 TRUE
   
#BETWEEN ~ AND : 피연산자가 범위 안에 있는 경우 참
	SELECT NAME, BASICPAY FROM INSA WHERE BASICPAY >= 2000000 AND BASICPAY<=2500000;
	SELECT NAME, BASICPAY FROM INSA WHERE BASICPAY NOBETWEEN 2000000 AND 2500000;
	SELECT NAME, BASICPAY FROM INSA WHERE NOT BASICPAY  BETWEEN 2000000 AND 2500000;
	위의 연산만으로 이루어진 것이 속도가 훨씬 빠르다. 함수를 사용하면 느려진다.

#LIKE : 주어진 문자열이 패턴과 일치하는지 여부 확인
    '%' : 여러문자 공통
	SELECT NAME FROM INSA WHERE NAME LIKE '홍%'; =>홍길동 홍길남 홍원신
	SELECT NAME FROM INSA WHERE NAME NOT LIKE '홍%';
	SELECT NAME FROM INSA WHERE NAME LIKE '%이%';
	
	1. 2번째 글자가 '미'인 것 찾기
	SELECT NAME FROM INSA WHERE NAME LIKE '_미%';
	2. 모두 출력하기
		SELECT NAME FROM INSA WHERE NAME LIKE '%%';
	'_' : 한문자 공통
	SELECT NAME FROM INSA WHERE NAME NOT LIKE '홍_';
	SELECT NAME FROM INSA WHERE NAME LIKE '홍_';
	
	'%' 는 0~N개 까지, '_' 는 단 하나의 문자를 나타내는 와일드카드
	
	%는 like에서만 사용가능하다.
    문자열 처리 시 오라클 내부 알고리즘상 LIKE 연산자보다
     INSTR (column | expression, 'string' [,m] [,n]) 함수가 더 빠르게 처리
	--ㄱ씨 출력하기 신기방기
	select name from insa where name>='가' and name<'나' ;

#ESCAPE
with tb AS( --tb 가상 테이블 이름
    select '김김김' name, '우리_나라' content from dual
    UNION ALL
    select '나나나' name, '자바%스프링' content from dual
    UNION ALL
    select '다다다' name, '우리나라' content from dual
    UNION ALL --합집합 한줄한줄라인을 합쳐서 하나로
    select '라라라' name, '웹%안드로이드' content from dual
	)
   '%' 나 '_' (와일드카드)가 포함된 데이터 추출하기 위해 활용
   -- select * from tb where instr(content,'%')!=0; --같은 내용
    select * from tb where content like '%#%%' ESCAPE '#';
	--#뒤의 문자는 검색하기 위한 문자다 #대신 아무 특수기호나 써도 상관없으나 ESCAPE를 통해서 등록해줘야함
	
#DECODE : 각각의 조건에 맞는 값에 대한 처리를 결과를 리턴 --편하지만 성능은 안좋음
	DECODE(expr,  search1,result1
                  [,search2,result2,...] [,default] );

	DECODE(a, 'b', 1, 2) 
      a라는 항목값이 'b'와 같다면 1, 같지 않으면 2를 출력하며 a 는 칼럼이 될수도있고, 특정값이 될수도 있음
      마지막값이 항상 default
	DECODE(a, 'b', 1) 
      a라는 항목값이 'b'와 같다면 1, 같지 않으면 null
	DECODE(a, 'b', 1, 'c', 2, 3) 
      a라는 항목값이 'b'와 같다면 1, 'c'와 같다면 2, 같지 않으면 3를 출력

#CASE ~ END : 조건에 따라 다른 결과를 리턴해야 하는 경우(DECODE 보다 성능 우수)

    - CASE 함수에서는 산술연산, 관계연산, 논리연산과 같은 다양한 비교가 가능하다. 또한 WHEN 절에서 표현식을 다양하게 정의할 수 있다.
    - CASE 명령어 다음에 기술하는 컬럼명/표현식과 조건, 결과에 표현되는 데이터들은 모두 데이터 타입이 동일해야 한다.

   1) 형식1 : CASE 컬럼명|표현식  WHEN 비교값
      CASE 뒤에 컬럼명 또는 표현식을 붙이고 특정 WHEN 절에 있는 값이 조건문의 결과에 일치하면  THEN 다음의 내용을 반환한다.

      'CASE 컬럼명|표현식 WHEN 비교값' 형식의 'CASE 컬럼명|표현식' 및 'WHEN 비교값'에 올수 있는 데이터 타입으로는 CHAR, VARCHAR2, NCHAR, NVARCHAR2 등이 있으며, 11g는 NUMBER 데이터 타입도 가능하다.

		 SELECT
			CASE 컬럼명|표현식
				   WHEN 비교값1 THEN 결과값1
				   WHEN 비교값2 THEN 결과값2
						 :
				   [ ELSE 결과값n ]
			END "칼럼명"
		 FROM 테이블명;

   2) 형식2 : CASE WHEN 조건문
      CASE 뒤에 WHEN이 바로 오는 형태로 WHEN 절은 조건문을 가지고 있고 특정 WHEN 절의 조건문이 TRUE일 경우 THEN 다음에 오는 값을 반환한다.

		 SELECT
			 CASE 
				 WHEN 조건문1 THEN 결과값1
				 WHEN 조건문2 THEN 결과값2
						   :
				   [ ELSE 결과값n ]
			 END "칼럼명"
		FROM 테이블명;

#ROWID pseudo 컬럼 
	1. 오라클에서 내부적으로 사용되는 컬럼을 수도우 컬럼 이라고 하며, ROWID, UROWID, ROWNUM등이 있다.
	2. ROWID는 데이터베이스에서 컬럼이 위치한 장소이며 수정할 수 없다.
	3. ROWID의 중요한 쓰임새는 다음과 같다.
      - single row를 찾아가는데 가장 빠른 방법이다.
      - 테이블에 행들이 어떻게 저장되어 있는지를 알려준다.
      - 데이블에서 행에 대한 unique identifier이다.

	ROWID의 의미 : AAAHbHAABAAAMXCAAA 
		AAAHbH : 32bits. Object 번호
		AAB : 10bits. TABLESPACE 번호(상대적 파일번호)
		AAAMXC : 22bits. BLOCK 번호
		AAA : 16bits. ROW 또는 Slot 번호 

#집합 연산자
   UNION : 첫 번째 SQL문의 결과와 두 번째 SQL문의 결과 중 중복된 데이터를 제거한다.
   UNION ALL : 첫 번째 SQL문의 결과와 두 번째 SQL문의 결과를 모두 출력한다.(중요)
   MINUS : 차집합. 첫 번째 SQL문 결과에는 있고, 두 번째 SQL문의 결과에는 없는 데이터를 출력한다.
   INTERSECT : 인터섹트는 두 번째 SQL문의 결과와 첫 번째 SQL문의 결과에 중복된 행만 출력(교집합)

//-----------------------------------------------------------------------------
#데이터 딕셔너리(Data Dictionary)
	1. TABLE과 VIEW들의 집합으로 DATABASE에 대한 정보를 제공하며,  DATABASE 생성시 SYS schema 안의 내부 TABLE로 구성

	SELECT COUNT(*) FROM DICTIONARY;
	SELECT COUNT(*) FROM USER_TABLES;
	SELECT COUNT(*) FROM TABS;
	SELECT * FROM TABS;  -- USER_TABLES 와 동일
	SELECT * FROM TAB;
	
#컬럼명 및 자료형등 출력
	SELECT * FROM col WHERE tname='INSA';    -- 간단히 출력
	SELECT * FROM cols WHERE table_name='INSA';
	SELECT column_name, data_type, data_length,data_precision FROM user_tab_columns WHERE table_name='INSA';
	SELECT column_name, data_type, data_length,data_precision FROM cols WHERE table_name='INSA';

#사용자에게 주어진 권한
SELECT * FROM USER_sys_privs;

#테이블 생성 스크립트 확인
		hr 사용자의 employees 테이블 작성 스크립트 확인
		select DMBS_METADATA.GET_DLL('TABLE', 'EMPLOYEES') from dual;

#자료형
	1. 문자 데이터 타입
		CHAR(n) : 고정길이 문자 데이터를 저장하며 최대 길이는 2000자이고 길이를 명시하지 않으면 기본 길이는 1
				 NLS(국가별 언어 집합)는 한글과 영문만 가능 하다.
				 남은 공간은 공백(space)로 채운다.
		VARCHAR2(n)(중요) : 가변 길이 문자 데이터를 저장하며 최대 길이는 4000자이고, 반드시 길이를 명시해야 한다.
					 NLS(국가별 언어 집합)는 한글과 영문만 가능하다.
					 VARCHAR 는 최대 2000개 문자를 저장하며 VARCHAR2와는 다르게 VARCHAR(10)로 선언하면  null을 채워 실제로는 10개의 공간을 사용한다. 하지만 VARCHAR2(10)는 필요한 문자까지만 저장하는 variable length이며 최대 4000개 문자까지 저장할 수 있다.
			 한글은 UTF-8(3byte)을 사용하여 저장하므로 VARCHAR2(9)는 한글 3글자만 저장 가능하다.
		VARCHAR는 어쨌든 공간을 모두 사용하므로 VARCHAR2를 사용하는 것이 권장된다.
		NCHAR, NVARCHAR2 : 다양한 언어의 문자 값(국가별 언어 집합 : NLS)을 저장하고 조회할 수 있다.
		NVARCHAR2(2000)으로 지정하면 2000은 최대로 저장할 수 있는 글자수로 실제로는 4000byte를 확보(NVARCHAR2에 저장가능한 최대 byte)하며 유니코드 문자열은 한글이나 영어 모두 2000자까지 저장가능하다. 하지만 한글이 UTF-8로 저장되는 경우는 1333자 까지만 저장할 수 있다.

	2. 숫자 데이터 타입
		NUMBER(P, S)
		 - P(precision:1~38)는 정밀도로 전체 자리수를 나타내며 기본 값이 38이고 S(Scale:-84~127)는 소수점 이하의 자릿수이다.
		 - 정수나 실수 저장하기 위한 가변길이의 표준 내부 형식이다.
		 - NUMBER(p,s) 는 고정형 소수점 방식으로 지정된 범위 내의 수치 자료만 입력 가능하다. 즉, 최소값과 최대값이 존재하는 수치 자료를 입력할 때 사용되며 NUMBER 는 부동형 소수점 방식으로 값에 범위 없이 모든 수치 자료를 저장 가능하게 한다. 
		사용 예
			NUMBER(5, 2) → p > s : 소수점 자리를 포함한 수치 자료[±0.01 ~ 999.99] (전체 다섯자리를 저장하되 소수 자리는 두자리) 
			NUMBER(4) → s = 0 : 정수부 수치 자료(Integer type)[± 0 ~ 9999] (소수점이하 저장 못하고 4자리 숫자만 저장가능하다)
			NUMBER(2, 3 ) → p <= s : 소수부 수치 자료(Float type). 0 저장가능[± 0.001 ~ 0.099] (가능하다.)
			소수점 3자리를 사용하며 수치 자료는 2자리만을 표현
			NUMBER(3,-1) → s < 0  : 정수부의 지정 자릿수 만큼 0[ ± 10 ~ 9990 ] (일의 자리에 0만 가능하다.)
			NUMBER(10, 3) : 1234567.6789 입력 시 1234567.679가 저장되며 12345678.678 입력 시 Overflow 에러가 발생 한다. (칸이 부족하면 무조건 반올림, 정수 공간이 부족하면 저장 못함)
			모든 NUMBER type 은 0 저장 가능 / NUMBER는 38자리까지 가능하다.

		    FLOAT (n) : ANSI Datatype(1 <= n <= 126)
		    BINARY_FLOAT : 32-bit floating point number(4 Byte)
		    BINARY_DOUBLE : 64-bit floating point number(8 Byte)
	 
	3. 날짜 데이터 타입
		 DATE : 『년/월/일 시:분:초』까지 저장하며, 기본적으로 년/월/일 정보를 출력한다.
		 TIMESTAMP : 『년/월/일 시:분:초.밀리초』까지 저장

	4. 대용량 자료 저장 타입
		CLOB : 입력되는 데이터가 대용량의 텍스트 유형(doc, txt, hwp 등)을 가질 때 저장할 수 있는 타입(최대 4GB) //일반적인 문자형태를 저장할 때
		CLOB는 데이터를 저장하는 공간도 다르고 검색 속도도 현저하게 떨어지므로 VARCHAR2를 우선적으로 사용하고 필요한 경우만 사용해야한다.
		BLOB : 입력되는 데이터가 이미지 유형 등의 이진 데이터를 저장할 수 있는 타입으로 4G 까지 저장 가능하다. //DB안에 이미지나 동영상 등을 직접 저장할때
		CLOB, NCLOB, BLOB, BFILE 등은 11g에서는 최대 8TB to 128TB 가능[(4 Gigabytes - 1) * (database block size)]

//---------------------------------------------------------------------------------------------------------------
#테이블 작성
	CREATE TABLE 테이블_이름 ( 
		  컬럼명  데이터타입 [DEFAULT 표현식] [제약조건] 
		  [, 컬럼명  데이터타입 [DEFAULT 표현식] [제약조건] ] 
		  [ ,...]
		  [ ,CONSTRAINT 제약조건명 제약조건]
	) [TABLESPACE tablespace명];
	기본키는 유일해야하마 절대로 널값이 올수없다...........................
	C는 체크제약, P는 프라이머리 키 제약
	제약조건의 이름 또한 유일해야한다.


#테이블에 필드(속성,컬럼) 추가
	ALTER TABLE 테이블이름 ADD (컬럼명 datatype [DEFAULT 값] [, 컬럼명 datatype]...);

#테이블의 컬럼폭 및 자료형 변경
    ALTER TABLE 테이블명 MODIFY (컬럼명 datatype [DEFAULT 값]  [, 컬럼명 datatype]...);
	alter table member modify (bigo varchar2(200))
	컬럼에 데이터가 존재하는 경우 컬럼의 폭을 줄일 경우 존재하는 데이터의 가장 긴 길이보다는 크거나 같아야 한다.
	컬럼에 데이터가 존재하는 경우 데이터와 변경하려하는 컬럼의 자료형이 일치해야 자료형 변경이 가능하다.

#밀리초까지 삽입
	insert into member (id,pw,name,birth,point,created) values ('d','d','후후후','2000-07-07',0, to_timestamp('201901071138555','YYYYMMDDHHMISSFF3');

#테이블의 필드명(속성,컬럼) 변경
	ALTER TABLE 테이블명 RENAME COLUMN 변경전컬럼명 TO 새로운컬럼명;
	alter table member rename column bigo to memo;

#테이블의 필드(속성,컬럼)  삭제
   ALTER TABLE 테이블명 DROP COLUMN 컬럼명;
	alter table member drop column memo;

#테이블명 변경 : 테이블, 뷰, 시퀀스, 시너님(별칭)의 이름을 변경
	RENAME old_name TO new_name;
	ex) rename member to join;

#기존 테이블을 이용하여 테이블 작성
	CREATE TABLE 테이블명 [(컬럼명 , 컬럼명 ,...)] AS subquery;
	컬럼명을 반드시 줘야한다. 안주면 에러나옴
	select num, name, to_DATE(substr(ssn,1,6)) birth, basicpay, sudang, basicpay+sudang pay from insa;
	
	*주의 : 제약조건 중 NOT NULL 속성만 복사 / 다른 제약 조건은 복사되지 않는다.
#테이블을 생성하며 서브쿼리를 이용하여 테이블 구조 복사
	create table insa2 as select num,name, city, basicpay from insa where 1=0;

#이미 구조가 복사된 상태에서 새로 가져와서 출력하기
	insert into insa2 (num,name,city, basicpay) (select num,name,city,basicpay from insa where city='서울');

#테이블 삭제
	DROP TABLE 테이블명 [CASCADE CONSTRAINTS] [PURGE];
	DROP TABLE로 데이터 삭제시 오라클에서는 우선적으로 휴지통으로 들어간다.
	drop table insa3;
	drop table join purge; -- 완전 삭제

#가상 컬럼(속성) 테이블 생성 --나중에 사용하기 귀찮은 수식들을 테이블을 생성할때 미리 생성해두는 가상의 컬럼 대신 속도의 저하가 있을 수 있고 데이터 삽입시 에러 발생
	
	1. 가상 컬럼은 테이블에있는 하나 이상의 컬럼값을 기반으로 계산식을 적용할 수 있는 기능(11g 부터 가능) 
	※ 특징 
	  - 가상 컬럼은 데이터베이스 내의 메타데이터로 저장된다.
	  - 물리적인 공간을 차지 하지 않는다.
	  - 파티션 테이블 사용 시 적당한 컬럼이 없을 경우 가상 컬럼을 통해 적용 가능하다.
	  - 가상 컬럼에 인덱스(함수 기반 인덱스) 생성이 가능하며, 통계 정보 수집도 가능하다.
	  - 다른 테이블의 컬럼을 이용한 가상 컬럼을 생성할 수 없다.
	  - 가상 컬럼에는 데이터를 INSERT 할 수 없다.

#가상 컬럼(속성) 형식 
	컬럼(속성)명 자료형 GENERATED ALWAYS AS ( 수식 ) VIRTUAL
		Alter table insa_score add(tot NUMBER(3) generated always as (com+excel+word) virtual
		);
		ex) 1. 두개를 한번에
				Alter table insa_score add(tot NUMBER(3) generated always as (com+excel+word) virtual, ave NUMBER(4,1) generated always as ((com+excel+word)/3) virtual
				);
			2. 점수 계산까지
				alter table insa_score add(grade varchar(9) generated always as (
				   case
					when com>=40 and excel >=40 and word >= 40  and round(((com+excel+word)/3),1)>=60 then '합격'
					when (com+excel+word)/3 <60 then '불합격'
					else '과락'
					end
				) virtual);

			3. 컬럼이 현재 7개 존재(num,com,excel,word,tot,ave,grade)하므로 4개만 넣을 수 없다.
			insert into insa_score values(1011,70,80,90); -- (x)
			4. 명시하면 가능
			insert into insa_score(num,com,excel,word) values(1011,70,80,90); -- (0)
			//자바쪽에서 할 수 있는건 다 자바에서 하는 걸로

#휴지통
	1. 휴지통이 없을 수도 있는데 왜그러는가 사용자를 추가할때 tablespace에 대한 올바른 할당이 이루어지지 않았기 때문에 발생한다.

#휴지통 확인
	SHOW RECYCLEBIN;  -- 휴지통 확인하기(사용자마다 따로 갖고있다)
	SELECT * FROM RECYCLEBIN; //show 보다 이것을 활용하도록 하자 	
	SELECT object_name, original_name, droptime, dropscn
		FROM RECYCLEBIN;

#휴지통 복원
	//original_name이 중복될 경우 object_name을 이용하여 복원한다.
	FLASHBACK TABLE  삭제전테이블명 TO BEFORE DROP;
	FLASHBACK TABLE "BIN 이름" TO BEFORE DROP;

#휴지통 복원(다른 이름으로 복구)
	FLASHBACK TABLE 삭제전테이블명 TO BEFORE DROP RENAME TO 새로운테이블명;
	테이블명은 중복이 불가능하므로 복구할 테이블명을 변경하거나 현재 존재하는 동명의 테이블명을
	수정해줘야한다.
	-- 삭제 테이블 복구 후 제약조건(기본키) 확인
	SELECT  a.table_name
		   ,b.column_name
		   ,a.constraint_name
		   ,a.constraint_type
		   ,a.search_condition
	FROM   user_constraints a
	JOIN   user_cons_columns b
	ON     a.constraint_name = b.constraint_name
	WHERE  a.table_name      = '테이블명'
	AND    a.constraint_type = 'P';
	복구된 테이블에 대한 제약정보를 확인하게 되면 “BIN$9~” 형식으로 표시된다. 해당 제약조건 이름을 원래의 이름으로 변경한다. 변경을 하지 않아도 동작에는 문제가 없지만, 추후 유지보수를 위한 식별이 가능하도록 변경한다.

#휴지통 전체 비우기
	PURGE RECYCLEBIN;
	Drop table insa2;
	Drop table insa3;

#특정 테이블만 비우기
	PURGE TABLE 비울테이블명;

//*****************************************

#트랜잭션 개념
    1. 한개의 테이블에 하나의 행만 추가 할 수 있다.
	2. 트랜잭션에 연관된 작업 3가지
    3. 오라클은 INSERT, UPDATE, DELETE 명령을 실행하면 자동으로 트랜잭션(작업)이 완료된 상태가 아니므로 다음중 하나을 이용하여 트랜잭션을 완료 해야 한다.

    COMMIT;    -- 트랜잭션 완료(INSERT, UPDATE, DELETE 완료)
                      -- 데이터베이스에 저장
    ROLLBACK;  -- 트랜잭션 취소(INSERT, UPDATE, DELETE 취소)
                      -- 데이터베이스에 저장 되지 않음
	트랜잭션이란 ? 영화 선택 => 날짜, 시간, 좌석수 => 좌석선택 => 결제 => 티켓발행
	영화 선택부터 티켓발행까지의 작업
	=>걍 작업 단위
   sqlgate 에서 날짜를 문자 형으로 추가 할때 오류 발생하는 경우 TO_DATE() 함수를 이용한다.
   
   예 : INSERT INTO test1(num, birth) VALUES (1, TO_DATE('2000-10-10', 'YYYY-MM-DD'));

#테이블에 데이터 추가(삽입)
    INSERT INTO 테이블명 [(컬럼명1, 컬럼명2, ...)] VALUES (값1, 값2, ...)
	
	INSERT INTO member(id,pw,name,birth,point) VALUES ('a','a','고고고','2000-10-10','010',0);
	이후 트랜잭션을 완료해줘야 완전히 삽입된 것이다.
	commit;

	1. 속성의 수와 삽입하려는 값의 개수가 다르면 삽입되지 않는다.
	2. NOT NULL로 제약조건을 가한 속성에 대해선 값을 넣지 않거나 ''(NULL을 의미)을 줄수 없다. 없다.

#시스템 날짜 추가
	insert into member(id,pw,name,birth,point,created)values('e','e','기기기','2000-07-07',0,SYSTIMESTAMP);
	commit;
	
#서브쿼리를 이용한 여러행 추가
	INSERT INTO 테이블명 [(컬럼명, 컬럼명, ...)] subquery;
	
#서브쿼리를 이용한 하나의 INSERT문을 이용 여러 테이블에 데이터 추가
-----------------------vㅔ리 중요------------------------
	INSERT ALL (Very 유용)
	   [INTO 테이블_1 VALUES (컬럼_1, 컬럼_2,...)]
	   [INTO 테이블_2 VALUES (컬럼_1, 컬럼_2,...)]
		.......
	Subquery;
	- ALL : 서브 쿼리의 결과 집합을 해당하는 insert 절에 모두 입력

	insert all
		into insa4(num,name,city,basicpay) values (num,name,city,basicpay)
		into insa5(num,name,buseo) values (num, name, buseo)
		select * from insa;
    
#서브쿼리를 이용한 조건에 만족하는 자료를 여러 테이블에 추가 
	  INSERT ALL | FIRST
			WHEN 조건절_1 THEN
				INTO [테이블_1] VALUES (컬럼_1, 컬럼_2, ...)
			WHEN 조건절_2 THEN
				INTO [테이블_2] VALUES (컬럼_1, 컬럼_2, ...)
				........
			ELSE
				INTO [테이블_3] VALUES (컬럼_1, 컬럼_2, ...)
	  Subquery;

	 - ALL : WHEN 조건절에 만족하는 모든 테이블에 INSERT
	 - FIRST : WHEN 조건절에 만족하는 첫번째 테이블에만 INSERT 

-----------------------vㅔ리 중요------------------------
	insert all --서브쿼리를 만족한 개수만큼 insert된다.
	//한개씩만 넣고 싶을때는 dual 테이블을 사용하자
	into insa2(num,name) values (100,'홍길동')
	into insa3(num,basicpay,sudang) values(100,1000000,100000)
	select * from dual;
  
//*****************************************
#데이터 수정
	UPDATE 테이블명  SET 컬럼명= 변경할값[, 컬럼명= 변경할값, ...]  [WHERE 조건];

	1. 조건을 주지 않으면 모든 행의 컬럼값을 수정한다.
	2. 한번에 하나의 테이블만 수정 할 수 있다.
	3. 기본키도 제약조건에 위배되지 않으면 변경 가능하다 그러나 잘 변경하지 않느다.
	4. 특별한 경우를 제외하고 일반적으로 기본키를 잘 변경하지 않는다.
	5. rollback,commit을 하지 않으면 다른 사람이 접근햇을대 시스템 락걸림
		
		select * from member;
		update member set pw = 'x' where name='기기기';
		update member set pw = 'v', birth='1999-10-10' where id='d';
		select * from member;
		update member set id = 'x' where id ='d';

	

#서브쿼리를 이용한 데이타 수정
	update insa_score set excel = excel +5 where num IN (select num from insa where mod(substr(ssn,8,1),2)=0);
	select * from insa_score;

#데이터 삭제 -- 구조는 삭제되지 않고 자료만 삭제된다.
	   DELETE [FROM] 테이블명 [WHERE 조건];
	   1.조건을 주지 않으면 모든 정보가 삭제 된다.

   
#데이터 복구
	1. 10분전의 insa 테이블의 데이터 (대신 10분전에 만든 테이블의 10분 이전의 데이터는 보지못함)
		select * from insa as of TIMESTAMP (systimestamp - INTERVAL '10' MINUTE);

	2. 10분 전의 데이터로 복구
		insert into insa (
			select * from insa as of TIMESTAMP (systimestamp - INTERVAL '2' DAY)
		);	

#ORA_ROWSCN
	1. 가장 최근에 SCN(System Change Number) 값을 반환.
	2. 테이블의 데이터가 수정된 시간을 관리하는 칼럼이 없을 경우 사용하면 유용.
	3. 테이블 삭제로부터 몇분 전 상태의 데이터 살리기

#truncate
TRUNCATE TABLE 테이블명;
   -- 모든 자료를 삭제하는 경우 DELETE 보다 빠름
   -- ROLLBACK 불가(자동 COMMIT)

#MERGE(병합)  => 원본에 대상테이블을 합친다. 매칭되는 것이 잇으면 수정 없으면 삽입
		MERGE INTO 테이블_명 별칭 (원본)
		  USING 대상테이블/뷰 별칭
		  ON ( 조인조건 )
		  WHEN MATCHED THEN
			UPDATE SET
				컬럼_1=값_1
				,컬럼_2=값_2
		  WHEN NOT MATCHED THEN
			INSERT (컬럼_1, 컬럼_2, ...)
				VALUES(값_1, 값_2, ...);

				create table insa2 as (
			select num, name, basicpay, city, buseo from insa where city='서울'
			);

		merge into insa2 in2
			using insa3 in3
			on (in2.num = in3.num)
			when matched then
				update set in2.basicpay = in2.basicpay+in3.basicpay
			when not matched then
				insert (in2.num, in2.name, in2.basicpay, in2.city, in2.buseo)
					values (in3.num, in3.name, in3.basicpay,in3.city, in3.buseo);

		select * from insa2;

//------------------------------------------------------------------------------------------

#그룹함수(집계함수)
	1. WHERE 절에 사용 불가, GROUP BY 를 사용하지 않는 경우 일반 속성과 같이 사용 불가능하다.
	2. GROUP BY 에 포함된 일반 속성만 그룹함수와 같이 표현 가능하다.
	3. AVG와 COUNT는 NULL 자료를 없는 것으로 계산하기 때문에 
	
#모든 그룹함수의 근원 GROUP BY
	1. 테이블의 행들을 원하는 그룹으로 나눌 때 사용되는 키워드이다.
	2. 그룹함수의 컬럼명을 SELECT 절에 사용하고자 하는 경우, GROUP BY 다음에 컬럼명을 추가한다.
    3. WHERE 절을 사용하여 행들을 그룹으로 나누기 전에 미리 조건에 맞지 않는 행들을 제외시킬 수 있다.
    4. GROUP BY 절에 컬럼의 위치 또는 컬럼 별칭을 줄 수 없다.
    5. 그룹 함수가 아닌 SELECT 절의 어떤 컬럼이나 표현식은 GROUP BY 절에 와야 한다.
    6. SELECT 절에서 나열된 컬럼 이름이나 표현식은 GROUP BY 절에서 반드시 명시해야 한다. 그러나 GROUP BY 절에서 명시한 컬럼 이름은 SELECT 절에서 명시하지 않아도 된다.
    7. GROUP BY 절을 이용하여 보다 적은 그룹으로 분류하여 처리하는 것이 가능하다.
    8. ROLLUP이나 CUBE 연산자를 사용하여 GROUPING(함수)한 결과에 대한 이해를 쉽게 한다.
    9. GROUPING SETS에 여러 개의 그룹핑 조건을 기술하여 원하는 그룹핑 결과를 얻을 수 있다.

#그룹에 대한 검색 조건 HAVING
	1. 그룹 또는 집계에 대한 검색 조건을 지정한다. HAVING은 SELECT문에 선언한 속성을 이용해서만 조건 지정이 가능하다.
	
#LISTAGG(컬럼명, '구분자') WITHIN GROUP(ORDER BY 나열규칙) ~ GROUP BY 컬럼명 : 11g
	1. 컬럼의 내용을 그룹화 하여 구분자로 구분 가로로 나열함.
    2. 나열 순서는 GROUP(ORDER BY 나열규칙) 에 기술
		select buseo, LISTAGG(name, ',') within group(order by num) "부서별 사원" from insa group by buseo;
		--group(order by num) 번호 오름차순으로 결합하겠다. 

#ROLLUP : 그룹화 하고 그룹에 대한 부분합
	1. select buseo, jikwi, sum(basicpay) from insa group by rollup(buseo, jikwi) order by buseo, jikwi;
	2. select buseo, nvl(jikwi,'전체합계'), sum(basicpay) from insa group by rollup(buseo, jikwi) order by buseo, jikwi;
	3. select nvl(buseo,'전체합계'), sum(basicpay) from insa group by rollup(buseo) order by buseo;

#CUBE
	1. ROLLUP 결과에 GROUP BY 절의 조건에 따라 모든 가능한 그룹핑 조합에 따른 결과 출력
		select buseo, nvl(jikwi,'전체합계'), sum(basicpay) from insa group by cube(buseo, jikwi) order by buseo, jikwi;
		마지막에 직위합계와 전체 총합계가 나옴

#GROUPING 함수
	1. GROUPING 함수는 ROLLUP이나 CUBE 연산자와 함께 사용하여 GROUPING 함수에 기술된 컬럼이 그룹핑 시 즉, ROLLUP이나 CUBE 연산 시 사용이 되었는지를 보여 주는 함수
	2. 즉, GROUPING 함수를 이용할 경우 출력되는 결과값 중 NULL값이 있다면 이 NULL값이 ROLLUP이나 CUBE 연산의 결과로 생성된 것인지, 원래 테이블상에 NULL값으로 저장된 것인지를 확인할 수 있다.
	3. GROUPING 함수는 결과값으로 0 또는 1을 리턴한다. 0값을 리턴하는 경우, 해당 인수로 쓰인 값이 ROLLUP이나 CUBE 연산시 사용되어 졌음을 나타내는 것이고, 1값을 리턴하는 경우 ROLLUP이나 CUBE 연산애 사용되지 않았음을 나타낸다.

#GROUP_ID()
	1. SELECT 문에서 GROUP BY로 분리되어 복제된 번호로 복제 횟수를 구분하도록 출력한다. 
	2. 번호가 0부터 시작되므로 n번 복제되었으면 n-1의 번호가 출력된다. 
		즉, GROUP BY의 확장으로 인하여 같은 grouping을 나타내는 값이 중복되어 결과 집합(result set)에 포함될 수 있으며, 
		이 때 중복된 그룹들을 구별하기위해 처음으로 중복되어 나타나는 행에 대하여 GROUP_ID 함수는 0을 반환하고, 나머지 경우에 대하여 1의 값을 반환한다. 그러므로 이 함수는 질의 결과에서 중복된 groupings를 필터링하는데 유용하다.

#GROUPING_ID(expr,...)
	1. 행과 관련되는 GROUPING 비트 벡터에 대응되는 수치를 반환한다.
    2. GROUPING_ID 는 여러 GROUPING 함수의 결과를 비트 벡터 (1과 0을 조합 한 문자열)에 연결 한 것과 동일하다. GROUPING_ID 를 사용하면 여러 GROUPING 함수를 사용하지 않아도 행의 필터 조건의 표기가 쉬워진다. GROUPING_ID 를 사용하면 요청하는 행이 단일 조건 ( GROUPING_ID = n )에 의해 식별되기 때문에 행 필터링이 쉬워진다. 이 기능은 하나의 테이블에 여러 집계를 저장하는 경우에 특히 유용하다.

#GROUPING_ID 함수의 처리로직
	1. 맨 먼저 GROUPING 함수처럼 표현식 값의 NULL 여부에 따라 0과 1을 반환한다.
	2. 그 다음에 이 값을 비트벡터(bit vector)로 만든다. 즉 2진수로 변환한다.
    3. 변환된 비트벡터를 다시 십진수로 변환한다.
    4. 십진수로 변환된 값을 반환한다.

#GROUPING SETS 함수
	1. GROUPING SETS 함수는 GROUP BY 절에서 그룹 조건을 여러 개 지정할 수 있는 함수이다.
	2. 개별로 그룹핑하여 UNION ALL한 경우와 동일하나, 성능적인 면에서 뛰어남
	
#분석함수
	1. 단일 속성과 함께 사용 가능하나 그룹함수처럼 그룹에서 연산한 결과를 하나만 출력하는 것이 아니라
		테이블의 행 수 만큼 가져온다.
#분석함수 종류
	1. RANK() OVER([PARTITION BY 그룹지을속성,... ORDER BY 순서 나타낼 속성]) ->PARTITION 사용시 그룹별 데이터가 나옵니다.
	2. COUNT(연산할 속성) OVER()
	3. AVG() OVER()
	4. SUM() OVER()
	5. FIRST_VALUE() OVER() 분석함수로 정렬된 값중 첫번째 값을 가져온다.
	6. PIVOT => 이건 별도의 공부가 필요하다 하나도 모름

#ROWNUM = 작거나 같다만 사용 가능
	1. 오라클에서 자동으로 붙여주는 행 번호 이 것은 ORDER BY와 항시 동일하지 않다.
	
	//중간 순위 데이터 가져오는 코드
	SELECT * FROM 
	(
		SELECT RNUM, TB.* FROM(
			SELECT * FROM 테이블명 
		)TB WHERE ROWNUM <=20
	)WHERE RNUM >= 11



/* 제약조건 확인*/

#제약조건 종류
	1. 개체 무결성(PRIMARY KEY,  UNIQUE)
		릴레이션에 저장되는 튜플(tuple)의 유일성을 보장하기 위한 제약조건.
		컬럼이 중복적인 값을 가질 수 없다.(예 : 회원아이디, 주민번호 등)
		PRIMARY KEY는 NULL 값을 가질 수 없다.
		하나의 테이블에 하나의 기본키만 지정가능하다.
		두개 이상의 컬럼을 이용하여 기본키를 지정할 수 있다.
		Ex)회원번호로 기본키를 줬을때 주민등록 번호에 대해서 유니크를 설정함으로써 무결성을 보장할 수 있다.
		primary key와 unique 제약조건의 칼럼은 자동으로 인덱스가 만들어 진다.
		
	2. 참조 무결성(FOREIGN KEY)
		참조 무결성은 릴레이션 간의 데이터의 일관성을 보장하기 위한 제약조건이다

	*  제약 조건이름 30자 까지 지정 가능하다.

#제약조건 확인
	1. SELECT * FROM USER_constraints WHERE table_name='테이블명'; 
	// 제약 이름, 제약 타입(P:기본키, C:NOT NULL 등, U:UNIQUE, R:참조키 등),제약 조건,참조 제약 조건 확인가능  
	// 어떤 컬럼에 제약조건이 부여되었는지 확인 불가(테이블명은 대문자로)

    2. SELECT * FROM USER_cons_columns;
    // 어떤 컬럼에 제약조건이 부여되었는지 확인 가능
    // 제약 조건의 종류는 확인 불가능


	3. 	SELECT u1.table_name, column_name, constraint_type,
		   u1.constraint_name 
		   FROM user_constraints u1
		   JOIN user_cons_columns u2
		   ON u1.constraint_name = u2.constraint_name
		   WHERE u1.table_name = UPPER('테이블명');
	// 컬럼 이름, 제약 조건, 제약 이름


	4.  SELECT fk.owner, fk.constraint_name , pk.TABLE_NAME parent_table, 
			fk.table_name child_table
			FROM all_constraints fk, all_constraints pk 
			WHERE fk.R_CONSTRAINT_NAME = pk.CONSTRAINT_NAME 
			AND fk.CONSTRAINT_TYPE = 'R'
			ORDER BY fk.TABLE_NAME;
	// 부 테이블, 자 테이블, 두테이블 간 제약조건 확인가능


	5. SELECT fk.owner, fk.constraint_name , fk.table_name 
			FROM all_constraints fk, all_constraints pk 
			WHERE fk.R_CONSTRAINT_NAME = pk.CONSTRAINT_NAME 
			AND fk.CONSTRAINT_TYPE = 'R'
			AND pk.TABLE_NAME = UPPER('테이블명')
			ORDER BY fk.TABLE_NAME;
	// 자신과 자식에 대한 제약조건과 자식 테이블 출력
		 
 
	6.	SELECT table_name FROM USER_constraints
		   WHERE constraint_name IN (
		SELECT r_constraint_name 
		   FROM user_constraints
		   WHERE table_name = UPPER('테이블명')
				 AND constraint_type = 'R'
		);
	// 자신이 참조하고 있는 테이블 출력(부모 테이블)
	
	7.  SELECT u1.table_name, column_name, constraint_type,
		   u1.constraint_name 
		   FROM user_constraints u1
		   JOIN user_cons_columns u2
		   ON u1.constraint_name = u2.constraint_name
		   WHERE u1.table_name = UPPER('test3');
	// 컬럼 이름, 해당 컬럼의 제약 타입, 제약 이름
	
	8.	SELECT constraint_name, table_name,  r_constraint_name, constraint_type,  search_condition
        FROM user_constraints;
	//제약 이름, 컬럼명, 참조된 제약 이름, 제약 조건
	
#제약 조건 이름 수정
		ALTER TABLE 테이블이름 RENAME CONSTRAINT "변경할제약조건이름" TO 새로운제약조건이름;
/*************************************************************************/	
	
#기본키 지정 ([]는 생략 가능)

	1. CREATE TABLE 테이블명 (
		컬럼명 데이터타입 [CONSTRAINT 제약조건명] PRIMARY KEY
		   :
		) 
	// 컬럼 레벨 기본키 설정 
	// 하나만 설정 가능

	2. CREATE TABLE 테이블명 (
		컬럼명 데이터타입 
		   :
		,[CONSTRAINT 제약조건명] PRIMARY KEY(컬럼명 [,컬럼명])
		);
	// 테이블 레벨 기본키 설정
	// 하나 이상의 컬럼을 이용하여 기본키로 지정 가능
	// 기본키는 하나가 베스트고 둘을 초과하게 되면 프로그램 상에 어려움이 발생할 가능성이 높다.

#기본키 삭제
	1. ALTER TABLE 테이블명 DROP PRIMARY KEY;
	2. ALTER TABLE 테이블명 DROP CONSTRAINT 제약조건이름;

#기본키 추가 : 이미 존재하는 테이블에 기본키 추가 ([]는 생략 가능)
	1. ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건이름 PRIMARY KEY(컬럼명[, 컬럼명]);
  
#기본키 비활성화
	1. ALTER TABLE 테이블명 DISABLE PRIMARY KEY CASCADE;

	2. SELECT constraint_name, table_name, status, r_constraint_name, constraint_type,  search_condition
            FROM user_constraints;  -- status 컬럼에서 확인

#기본키활성화
	1. ALTER TABLE 테이블명 ENABLE CONSTRAINT 제약조건명;

	2. SELECT constraint_name, table_name,  status, r_constraint_name, constraint_type,  search_condition
            FROM user_constraints;
			
/*************************************************************************/  
   
#유일성 제약(UNIQUE)
	1. UNIQUE 제약
	   테이블에서 지정한 컬럼의 데이터가 중복되지 않고 유일하다.
	   null을 허용할 경우 null은 중복이 가능하다.
	   UNIQUE는 두개 이상 지정 가능 하다.

#유일성 설정
	1.	CREATE TABLE 테이블명 (
			컬럼명 데이터타입 [CONSTRAINT 제약조건명] UNIQUE
			   :
		);
	//컬럼 레벨 유일성 설정
	2.	CREATE TABLE 테이블명 (
		컬럼명 데이터타입 
		   :
		,[CONSTRAINT 제약조건명] UNIQUE(컬럼명)
		);
	//테이블 레벨 유일성 설정


#유일성 제약 추가 (UNIQUE)
	1.	ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건이름 UNIQUE(컬럼);
	
#유일성 제약 삭제 (UNIQUE)
	1.  ALTER TABLE 테이블명 DROP CONSTRAINT 제약조건이름;


/*************************************************************************/ 
#NOT NULL 이란?
    1. 테이블에서 지정한 컬럼의 데이터가 NULL(또는 '') 값을 갖지 못한다. ' ' 처럼 공백이 있는 경우는 null이 아니다.

#NOT NULL 설정

	1.  CREATE TABLE 테이블명 (
			컬럼명명 자료형 NOT NULL, 
			:
		);

#NOT NULL 추가 (존재하는 테이블에)
	1.	ALTER TABLE 테이블명 MODIFY 컬럼명 NOT NULL;
	2.	ALTER TABLE 테이블명 MODIFY (컬럼명 자료형 NOT NULL);
	3.	ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 CHECK(컬럼명 IS NOT NULL);

#NOT NULL 제거
	1.	ALTER TABLE 테이블명 MODIFY 컬러명 NULL;
	2.	ALTER TABLE 테이블명 DROP CONSTRAINT 제약조건이름;

#NOT NULL 비활성화
	1. ALTER TABLE 테이블명 DISABLE CONSTRAINT 제약조건이름 CASCADE;

	1. SELECT constraint_name, table_name,  status, r_constraint_name, constraint_type,  search_condition
            FROM user_constraints; 


/*************************************************************************/ 

#DEFAULT(초기값)란?
    1.  INSERT할 때 값을 입력하지 않은 경우 DEFAULT에서 설정한 값으로 입력
    2.  INSERT와 UPDATE 문에서 특정 값이 아닌 디폴트값을 입력할 수 도 있다.

#DEFAULT 설정
	1.	CREATE TABLE 테이블명 (
			컬럼명 자료형 DEFAULT 값, 
				  :
		);

#DEFAULT 제거
	1.	ALTER TABLE 테이블명 MODIFY 컬럼명 DEFAULT NULL;
	
#DEFAULT 추가
	1.	ALTER TABLE 테이블명 modify 컬럼명 default 디폴트값;

#DEFAULT 값 확인
	1.	SELECT column_name, data_type, data_precision, data_length, nullable, data_default 
            FROM user_tab_columns WHERE table_name='테이블명';
	2. 	select * from cols where table_name='TEST'

/*************************************************************************/ 
#CHECK 제약 추가 //왠만하면 사용하지 않는게 좋다. 수정과 삭제에 추가적인 검사가 요구되기 때문에 힘들어짐
    1.  CREATE TABLE 테이블명 (
          컬럼명 데이터타입 [CONSTRAINT 제약조건명] CHECK(조건)
                :
		);
	//컬럼 레벨 추가
	
	2.	CREATE TABLE 테이블명 (
			  컬럼명 데이터타입 
					:
			  ,CONSTRAINT 제약조건명 CHECK(조건)
		);
	//테이블 레벨 추가
	
#CHECK 예제
	1. gender 컬럼에 남자와 여자만 입력을 허용 할 경우
		gender VARCHAR2(6) CHECK(gender IN ('남자남자', '여자'))

	2. score 컬럼에 0~100 점만 입력을 허용 할 경우
		score NUMBER(3) CHECK(score BETWEEN 0 AND 100)

	3. alter table test add (gender varchar2(6)CHECK(gender IN ('남자', '여자')));
		=> 컬럼 추가시 기존 컬럼들에 데이터가 존재한다면 NOT NULL할 수 없다.

#CHECK 제약 추가 (존재하는 컬럼에)
	1.	ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 CHECK (조건);
	예제 - alter table test add constraint ck_test_score CHECK (score between 0 and 100);

	#체크제약 생성시 다른 컬럼에 대한 체크제약을 넣을수는 없다.
		alter table test add (edate Date check(edate>sdate)) =>error
	#따라서 체크제약 추가하는 방식으로 설정해야한다.
		alter table test add (edate Date);
		alter table test add (sdate Date);
		alter table test modify constraint ck_test_date CHECK (sdate<=edate);

#CHECK 제약 삭제
	1.	ALTER TABLE 테이블명 DROP CONSTRAINT 제약조건이름;


/*************************************************************************/
#기타 제약조건 제거
	1.	ALTER TABLE 테이블명 DROP CONSTRAINT 제약조건이름;
/*************************************************************************/


#참조(외래, FOREIGN)키란?
		1.	두 테이블의 데이터 간 연결을 설정하고 강제 적용하는 데 사용되는 열이다. 한 테이블의 기본 키 값이 있는 열을 다른 테이블에 추가하면 테이블 간 연결을 설정할 수 있다. 이 때 두 번째 테이블에 추가되는 열이 외래 키가 된다.
		2.	부모 테이블이 먼저 생성된 후 자식 테이블(foreign key를 포함하는 테이블)이 생성되어야 한다.
		3.	FOREIGN KEY는 부모 테이블의 PRIMARY KEY, UNIQUE만 참조할 수 있고, 컬럼의 값과 일치하거나 NULL을 허용하는 경우 NULL 값이어야 한다.
		4.	부모테이블의 컬럼명과 자식테이블의 컬럼명은 일치하지 않아도 되지만 자료형은 일치해야 한다.
		5.	참조 무결성 제약조건에서 부모 테이블의 참조 키 컬럼에 존재하지 않는 값을 자식 테이블에 입력하면 오류가 발생한다.
		6.	ON DELETE SET NULL은 자식 테이블이 참조하는 부모 테이블의 값이 삭제되면 자식 테이블의 값을 NULL 값으로 변경시킨다.
		7.	ON DELETE CASCADE 옵션을 지정하면 부모 테이블의 부모데이터가 지워지면 자식 테이블의 자식데이터도 지워진다.
		8.	자식 테이블이 존재하는 경우 부모 테이블은 제거가 불가능 하다.
		9.	DROP TABLE에서 CASCADE CONSTRAINTS 옵션을 부여 하면 자식 테이블이 존재 해도 부모 테이블이 제거가 가능하다.
		10. 제거는 자식부터 추가는 아버지부터
		11. 기본키이면서 참조키인 관계 : 식별관계
		
#참조키 생성		
		1. CREATE TABLE 테이블명 (
			   컬럼명 자료형
					   :
			  ,CONSTRAINT 제약조건이름 FOREIGN KEY(컬럼명 [,컬럼명])
						  REFERENCES 참조할테이블(컬럼명 [,컬럼명])
						  [ON DELETE CASCADE]
			);

#참조키 삭제
		1. ALTER TABLE 테이블명 DROP CONSTRAINT 참조키제약조건명;
		
#테이블 삭제와 함께 관계 제거 (위험)
		1. Drop table test1 cascade constraint purge;
		
#존재하는 테이블에 참조키 추가
		1. ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 FOREIGN KEY(컬럼명[, 컬럼명]) REFERENCES  참조할테이블명(컬럼명[, 컬럼명]);
		
#자기 참조 테이블 (대분류, 중분류 할때

	1.	create table test1 (
		num NUMBER primary key,
		snum NUMBER,
		subject VARCHAR2(30) NOT NULL,
		constraint fk_test1_num FOREIGN KEY(snum)
			REFERENCES test1(num)
		);
		
#참조키 문제
   -- hr 사용자(스키마)의 다음 7개의 테이블의 구조를 분석한다.
	   테이블명, 컬럼명, 자료형, 기본키, 참조키등의 제약조건등
   -- test라는 이름으로 사용자를 추가하고 test 사용자에
       hr 사용자의 테이블과 동일한 구조(자료형, 제약조건 등)로 7개의 테이블을 작성한다.
   -- 테이블 하나당 최소 3개 이상의 데이터(레코드)를 추가한다.
   	   
      COUNTRIES
      DEPARTMENTS
      EMPLOYEES
      JOBS
      JOB_HISTORY
      LOCATIONS
      REGIONS


/*************************************************************************/  
***매우중요
INSERT INTO test2(num,id,content) VALUES (1,'a','aaa');
--ORA-02291: integrity constraint (SKY.FK_TEST2_ID) violated - parent key not found
/*************************************************************************/  


#조인
-----------------------------------------------
- 회원기본 테이블
아이디   패스워드    이름
1       1          a
2       2          b
3       3          c

- 회원상세 테이블
아이디   전화번호    이메일
1       11          aa
3       33          cc

- 회원기본 테이블과 회원상세 테이블을 아이디를 기준으로 EQUI 조인한 결과는 ?
아이디    패스워드    이름    전화번호    이메일
1       1          a      11        aa
3       3          c      33        cc

- 회원기본 테이블과 회원상세 테이블을 아이디를 기준으로 LEFT OUTER 조인한 결과는 ?
아이디    패스워드    이름    전화번호    이메일
1       1          a      11        aa
2       2          b      null      null
3       3          c      33        cc

/*************************************************************************/  

#동등 조인
	1. 두 개 이상의 테이블에 관계되는 컬럼들의 값들이 일치하는 경우에 사용하는 가장 일반적인 join 형태로 WHERE 절에 '='(등호)를 사용한다.
	2. EQUI JOIN은 단순 JOIN 또는 내부 JOIN이라고도 한다. (내부 조인이 상위 개념)
	3. JOIN 조건의 컬럼은 UNIQUE가 아니어도 가능하다.

#동등 조인 형식-1 (조건 간 혼동이 생길 가능성이 높음)
	1. SELECT 테이블명1.컬럼명, 테이블명2.컬럼명 FROM 테이블명1, 테이블명2 [, 테이블명3 ]
         WHERE 테이블명1.컬럼명1=테이블명2.컬럼명1  [AND 조건];

#동등 조인 형식-2
    1. SELECT 컬럼명, 컬럼명 FROM 테이블명1
                JOIN 테이블명2 USING (컬럼명1)
                JOIN 테이블명3 USING (컬럼명2);

#동등 조인 형식-3 (많이 쓰이므로 중요)
    1. SELECT 테이블명1.컬럼명, 테이블명2.컬럼_명 FROM 테이블명1
               JOIN 테이블명2 ON 테이블명1.컬럼명1=테이블명2.컬럼명1;

#동등 조인 형식-4 (부하가 많이 걸리기 때문에 가급적 사용을 자제) (aka.자연조인)
    1. SELECT 컬럼명, 컬럼명 FROM 테이블명1 
                NATURAL JOIN 테이블명2;

    - 만약 MATCH되는 컬럼이 여러 개 일 경우, NATURAL JOIN에서는 특정 컬럼을 지정하지 않으므로 MATCH되는 컬럼 모두를 연결 조건으로 사용한다.
    - NATURAL JOIN의 경우 MATCH되는 컬럼이 여러 개일 경우 모두 join조건으로 사용하므로, 실행속도나 performance면에서 좋지 않다.

#동등 조인 예제
	-- 책코드(b_id), 책이름(title) 단가(price) 고객이름(g_name), 수량(p_su), 금액(price * p_su)
	-- book : b_id, title
	-- danga : b_id, price
	-- panmai : b_id, p_su, g_id(고객코드)
	-- gogaek : g_name, g_id(고객코드)
	
	--형식1
		1. select b.b_id, title, price, g_name, p_su, price * p_su amt from book b, danga d, panmai p, gogaek g where b.b_id = d.b_id and b.b_id = p.b_id and p.g_id = g.g_id;
	--형식2	
		1. select b_id, title, price, g_name, p_su, price * p_su amt from book join danga using(b_id) join panmai using(b_id) join gogaek using(g_id);
	--형식3
		1. select b.b_id, title, price, g_name, p_su, price * p_su amt 
			from book b join danga d ON b.b_id = d.b_id
			join panmai p on b.b_id = p.b_id
			join gogaek g on p.g_id = g.g_id;
	--형식4
		1. select b_id, title, price, g_name, p_su, price * p_su amt 
			from book
			natural join danga
			natural join panmai
			natural join gogaek;

/*************************************************************************/  

#외부 조인
	1. JOIN 조건을 만족하지 않는 행을 보기 위한 추가적인 join의 형태이다. 일반적인 join으로 얻을 수 없는 데이터를 구하고 싶을 때 사용하며, '+' 연산자를 사용한다. 
	2. OUTER JOIN은 조인 조건의 양측 컬럼 값 중에서 하나가 NULL인 경우에도 조인 결과를 출력할 수 있는 방법이다.
	3. EQUI JOIN은 조인 조건에서 양측 컬럼 값 중에서 하나라도 NULL이면 '=' 비교 결과가 거짓이 되어 조인 결과로 출력되지 않는다.

#LEFT 외부 조인
	1. SELECT 테이블1명.컬럼명, 테이블2명.컬럼명 FROM 테이블1명, 테이블2명
                WHERE 테이블1명.컬럼명=테이블2명.컬럼명(+);
				
    2. SELECT 테이블1명.컬럼명, 테이블2명.컬럼명 FROM 테이블1명
                LEFT OUTER JOIN 테이블2명
                ON 테이블1명.컬럼명=테이블2명.컬럼명;

	//왼쪽 테이블의 데이터는 모두 출력하고 오른쪽 테이블은 조건에 맞는 데이터만 출력
    

#RIGHT 외부 조인 (상당히 빈번하게 사용되는 조인)
	1. SELECT 테이블1명.컬럼명, 테이블2명.컬럼명 FROM 테이블1명, 테이블2명
                WHERE 테이블1명.컬럼명(+)=테이블2명.컬럼명;

    2. SELECT 테이블1명.컬럼명, 테이블2명.컬럼명 FROM 테이블1명
                RIGHT OUTER JOIN 테이블2명
                ON 테이블1명.컬럼명=테이블2명.컬럼명;
				
	//오른쪽 테이블의 데이터는 모두 출력하고 왼쪽 테이블은 조건에 맞는 데이터만 출력

#FULL 외부 조인(LEFT와 RIGHT OUTER JOIN 결합형태)

	1. SELECT 테이블1명.컬럼명, 테이블2명.컬럼명
		FROM 테이블1명 FULL OUTER JOIN 테이블2명 ON 테이블1명.컬럼명=테이블2명.컬럼명;

/*************************************************************************/
#NON-EQUI 조인 => 잘안쓰임
	1. 두테이블에 관련성있는 컬럼이 없고 한테이블 컬럼의 값이 다른 테이블 컬럼에 포함된 경우 사용(= 대신 >, BETWEEN 사용)
		select b.b_id, title, price
		from book b
		join danga d on b.b_id > 'b-1'; //b-1보다 큰 아이디의 책만 출력
#CROSS JOIN
	1. 상호 조인은 조인에 포함된 테이블의 카티션 곱(Cartisian Product)을 반환한다.
		// a테이블 컬럼수 * b테이블 컬럼수 만큼 출력
#SELF JOIN => 공통 저자
	1. 자신의 테이블을 alias를 사용하여 마치 두 개의 테이블처럼 JOIN하는 형태이다.
	
/*************************************************************************/

#기존 테이블에 다른 테이블의 데이터 삽입(속성 수가 다른 경우에도) USING UPDATE JOIN VIEW
	//tb_a 테이블의 내용(new_addr1, new_addr2)을 ta_b 테이블의 내용(n_addr1, n_addr2)로 변경
	//이 경우 조인 조건의 컬럼은 unique 속성이어야 하고 관계 1:1 이어야 가능하다. 아니면 ORA-01779 발생
	
		CREATE TABLE tb_a
			(
				id  NUMBER PRIMARY KEY
				,addr1  VARCHAR2(255)
				,addr2 VARCHAR2(255)
				,new_addr1 VARCHAR2(255)
				,new_addr2 VARCHAR2(255)
			);

		CREATE TABLE tb_b
			(
				id  NUMBER PRIMARY KEY
				,n_addr1 VARCHAR2(255)
				,n_addr2 VARCHAR2(255)
			);
			
		update 
			(
				select 
					a.new_addr1, a.new_addr2,
					b.n_addr1, b.n_addr2
				from tb_a a, tb_b b
				where a.id = b.id
			) 
			set 
				new_addr1 = n_addr1,
				new_addr2 = n_addr2;
			commit;
			select * from tb_a;
			select * from tb_b;

			
/*************************************************************************/
#INLINE VIEW, 서브쿼리(subquery)
	1. inline view는 SELECT, INSERT, UPDATE, DELETE 문이나 다른 하위 쿼리 내부에 중첩된 SELECT 쿼리 이다.
	2. inline view는 식이 허용되는 모든 위치에서 사용할 수 있으며 단독으로 실행 가능 하다.

	3. 다음의 SQL 명령절에 사용이 가능하다.
		 - WHERE 절
		 - HAVING 절
		 - INSERT 문장의 INTO 절
		 - UPDATE 문장의 SET 절
		 - SELECT 또는 DELETE 문장의 FROM 절

	4. subquery에는 두 종류의 연산자가 사용.
		1. 결과로 하나의 행과 하나의 컬럼을 반환 받아 연산 할 수 있는 연산자
		   >, <, >=, <=, =, !=
			SELECT num, name, basicpay  FROM insa
			WHERE basicpay < (SELECT AVG(basicpay) FROM insa);

		2. 결과로 여러 행을 반환 받아 연산할 수 있는 연산자 (IN, ANY, ALL)
			SELECT num, name, basicpay  FROM insa
			WHERE num IN (SELECT num FROM insa WHERE MOD(SUBSTR(ssn,8,1),2)=0);

		3. ANY(SOME) 연산자의 사용
			ANY 연산자는 IN 연산자와 달리 어떤 특정한 값이 아닌 범위로 비교연산을 처리한다.
			ANY 연산자는 서브쿼리에서 리턴되는 어떠한 값이라도 만족을 하면 조건이 성립된다.
			select num, name, basicpay, ssn from insa 
			where num =  ANY (SELECT num FROM insa WHERE MOD(SUBSTR(ssn,8,1),2)=0);
		
			select num, name, basicpay, ssn from insawhere num > any (1003,1005,1007);
			//1003 보다 큰 모든 데이터
			select num, name, basicpay, ssn from insa 
			where num >  ANY (SELECT num FROM insa WHERE MOD(SUBSTR(ssn,8,1),2)=0);
			// 여자의 num 중 가장 작은 num 보다 큰 모든 데이터 출력 
			
		4. ALL 연산자의 사용
			ALL 연산자는 IN 연산자와는 달리 어떤 특정한 값이 아닌 범위로 비교연산을 처리한다.
			ALL 연산자는 서브쿼리에서 리턴되는 모든 값을 만족하면 조건이 성립된다.
			ANY는 OR의 개념, ALL은 AND의 개념과 유사하다.
			
			//서울사람 보다 급여를 많이 받는 사람은
			 select num,name, city, basicpay from insa
			 where basicpay > all (select basicpay from insa where city='서울');

//*******************************************************
#WITH
	1. 서브쿼리를 미리 블럭으로 정한후 사용(여러번 사용될 경우 간결)
		with tb as(
			select b.b_id,title,price,p.g_id,p_su,p_date
			from book b
			join danga d on b.b_id = d.b_id
			join panmai p on b.b_id = p.b_id
			join gogaek g on p.g_id = g.g_id;
		)

//********************************************************
#EXISTS (subquery) : 하나라도 존재하면 참
    1. 이그지스트 조건은 mainquery로 리턴되는 subquery의 결과 행의 존재 여부를 체크하여 존재하면 TRUE를 반환한다.
		EXISTS 조건 대신 IN 조건을 사용해서 표현할 수도 있다.

	2. select b_id, title from book
		where exists (select * from panmai where p_su > 10 and substr(b_id,1,1) = 'a');
		는 아래와 동일. 조건에 만족하는 데이터가 존재하므로
		select b_id, title from book
//*********************************************************
#상관 하위 부질의 (연산에 대한 부하가 너무 커서 쓰지말래)
    1. 상호 연관 서브 쿼리(correlated sub query)는 sub query가 main query의 값을 이용하고, 
		그렇게 구해진 sub query의 값을 다시 main query가 다시 이용하게 된다.
		select num, name, basicpay, 
		(select count(basicpay)+1 from insa b where b.basicpay > a.basicpay) 순위 
		from insa a order by 순위;
		
//*********************************************************


#VIEW? =>DB에 따라 데이터를 가지고 있을 수도 없을 수도 있다
	1.  뷰란 이미 특정한 데이터베이스 내에 존재하는 하나 이상의 테이블에서 사용자가 얻기 원하는 데이터들만을 정확하고 편하게 가져오기 위하여
		사전에 원하는 컬럼들 만을 모아서 만들어 놓은 가상의 테이블로 편리성 및 보안에 목적이 있다.
	2.  부하가 많이 생기기 때문에 실무에서 뷰를 잘 사용하지 않는다. 그래서 기본적으로 권한을 주지 않는다.
	3.  따라서 우리는 PLSQL을 배워야 한다.
	4.  한번에 두 개의 테이블에 (추가, 수정, 삭제) 불가 단순 뷰만 가능하다.
	5.  하나의 테이블로 만든 뷰에서는 추가, 수정, 삭제가 가능하다.
	6.  VIEW도 조인 연산이 가능하다. 
	
#VIEW 생성 권한 부여
	1. 사용자 권한 확인
	2. 사용자 계정
		SQL>SELECT * FROM user_sys_privs;
	3. 사용자 권한 부여(RESOURCE롤의 권한 만으로는 뷰를 생성할 수 없다.)
	4. SYS 계정
	5. 서버에 있는 모든 시스템 권한 출력
		CMD>sqlplus sys/암호 as sysdba
		SQL>SELECT * FROM system_privilege_map;
	6. 사용자에게 뷰를 만들 수 있는 권한 부여
		SQL>GRANT CREATE VIEW TO 사용자;
	7. 권한 확인
		SQL>CONN 사용자/암호
		SQL>SELECT * FROM user_sys_privs;
			
#VIEW 생성
	1. CREATE [OR REPLACE]  VIEW 뷰이름 AS subquery (replace 있으면 기존것 수정, 없으면 새로 생성 => 위험)
	//연산을 속성에서 한뒤 저장하고 싶을 경우에 별명을 반드시 지정해줘야한다.

#VIEW 삭제
	1. DROP VIEW 뷰이름;
	2. SELECT view_name, text FROM user_views;

#VIEW 정보 확인
	1. SELECT view_name, text FROM user_views;
	2. SELECT * FROM tab;   -- tabtype:view로 표시
	3. SELECT * FROM col;
	4. DESC 뷰이름;

#WITH CHECK OPTION 이용한 제약 검사
    뷰를 통해 참조 무결성(reference integrity)을 검사할 수 있고 DB 레벨에서의 constraint 적용이 가능하다. 즉, WITH CHECK OPTION 절을 사용한 뷰에서 INSERT와 UPDATE를 수행하면 에러가 발생한다.

#VIEW를 통한 데이터 생성,수정,삭제
//*****************************************
#머트리얼라이즈 뷰(MATERIALIZED VIEW)<express 버전은 지원 안함>
	1. 실제 데이터(통계정보)를 자신이 가지고 있으며, master 테이블이라고 불리는 기준이 되는 테이블에 INSERT, UPDATE, DELETE가 발생하면 새로운 데이터를 MATERIALIZED 뷰에 반영

	2. SUM, MIN, MAX, AVG, COUNT 등 그룹함수를 미리 계산해 놓을 때 사용
	3. USER_SEGMENTS 에서 확인 가능

#옵션
	1. BUILD IMMEDIATE : MView 생성과 동시에 데이터들도 생성
	2. BUILD DEFERRED : MView를 생성은 하지만, 그 안의 데이터는 추후에 생성
	3. REFRESH :  MView의 데이터를 새로 고치는 시기와 방법을 결정
		1) 시기
		  1) ON COMMIT - 기초 테이블에 COMMIT 이 일어날 때 REFRESH 가 일어나는 방안이며, 이는 1 개의 테이블에 COUNT(*), SUM(*)과 같은 집합 함수를 사용하거나, MView에 조인만이 있는 경우 GROUP BY 절에 사용된 컬럼에 대해 COUNT(col) 함수가 기술된 경우만  사용이 가능
		  2) ON DEMAND - 사용자가 DBMS_MVIEW 패키지 (REFRESH, REFRESH_ALL_MVIEWS, REFRESH_DEPENDENT)를 실행 한 경우 Refresh 되는 경우
		2) 방법
		  1) COMPLETE - MView의 정의에 따라 MView의 데이터 전체가 Refresh 되는 것으로 ATOMIC_REFRESH=TRUE와 COMPLETE으로 설정한 경우
		  2) FAST - 새로운 데이터가 삽입될 때마다 점진적으로 REFRESH 되는 방안
		  3) FORCE - 이 경우 먼저 FAST REFRESH가 가능한지 점검 후 가능하면 이를 적용하고, 아니면 COMPLETE REFRESH를 적용(디폴트)
		  4) NEVER - REFRESH를 쓰지 않는다.
	4. ENABLE QUERY REWRITE : MView 생성시 이 옵션을 주어야만 임의의 SQL문장을 처리시 QUERY REWRITE를 고려한다. 이는 쿼리 재작성의 기능이다. 이전의 쿼리를 수정 하지 않고 재작성이 가능 한 기능


#사용자에게 권한 설정
   - QUERY REWRITE : 쿼리 재작성 권한


//*****************************************
#SEQUENCE
	1. 특정 컬럼에 대해 유일한 값을 연속적으로 생성시키기 위해 사용하는 방법으로 오라클에서 제공하는 것이 sequence라는 객체이다.
	   - 시퀀스이름.NEXTVAL : 다음 시퀀스값
	   - 시퀀스이름.CURRVAL : 현재 시퀀스값


#SEQUENCE 만들기
	1. CREATE SEQUENCE 시퀀스명
		[INCREMENT BY n]	/* n= 증가치  */
		[START WITH n]	/* n= 시작값 */
		[[NO]MAXVALUE n]	/* n= 최고값  */ nomaxvalue하면 시스템 최대값까지
		[[NO]MINVALUE n]	/* n= 최소값  */ cycle 사용시 minvalue로 돌아옴
		[[NO]CYCLE]		/* MAXVALUE에 도달하면 초기값 부터 다시 시작 여부 */
		[[NO]CACHE n]	/* 미리 캐시 해 두는 개수  */cycle 있으면 반드시 cache 필요
		/*CACHE란? */
#SEQUENCE 삭제
	1. DROP SEQUENCE 시퀀스명;

#SEQUENCE 목록 확인
	1. SELECT * FROM seq;
	1. SELECT * FROM user_sequences;

#사용자에게 시퀀스를 만들 수 있는 권한이 없는 경우 권한 부여(RESOURCE 롤에 기본적으로 시퀀스를 만들수있는 권한 있음)
	SYS 계정(이미 권한이 있으므로 주지 않아도 됨)
	CMD>sqlplus sys/암호 as sysdba
	SQL>GRANT CREATE SEQUENCE TO 사용자;

#부여된 권한 확인
	SQL>CONN 사용자/암호
	SQL>SELECT * FROM USER_SYS_PRIVS;

//*****************************************
#계층적 질의
	1. 오라클에서는 계층적인 데이터를 저장한 컬럼으로부터 데이터를 검색하여 계층적으로 출력할 수 있는 기능을 제공한다.
    2. SELECT 문에서 START WITH와 CONNECT BY 절을 이용하여 데이터를 계층적인 형태로 출력할 수 있다. 
	3. 부모가 누구인지에 대한 컬럼이 존재 해야한다.
   - START WITH : 절 계층적인 출력 형식을 표현하기 위한 최상위 행
   - CONNECT BY : 절 계층관계의 데이터를 지정하는 컬럼
   - PRIOR 연산자 : CONNECT BY는 PRIOR 연산자와 함께 사용하여 부모 행을 확인할 수 있다. PRIOR 연산자의 위치에 따라 top-down 방식인지 bottom up 방식인지를 결정한다. PRIOR 연산자가 붙은 쪽의 컬럼이 부모 행이 된다.
   - WHERE 절 : where 절이 JOIN을 포함하고 있을 경우 CONNECT BY 절을 처리하기 전에 JOIN 조건부를 적용하여 처리하고, JOIN을 포함하고 있지 않을 경우 CONNECT BY 절을 처리한 후에 WHERE 절의 조건을 처리한다.
   - LEVEL : 계층적 질의문에서 검색된 결과에 대해 계층별로 레벨 번호 표시, 루트 노드는 1, 하위 레벨로 갈수록 1씩 증가

   SELECT LEVEL V FROM DUAL CONNECT BY LEVEL <= 20;


//*****************************************
	CREATE TABLE exam(
		num  NUMBER  PRIMARY KEY
		,dname  VARCHAR2(50) NOT NULL
		,loc        VARCHAR2(50)
		,parent   NUMBER
	);

	INSERT INTO exam VALUES(1, '공과대학', NULL, NULL);
	INSERT INTO exam VALUES(2, '정보미디어학부', NULL, 1);
	INSERT INTO exam VALUES(3, '메카트로닉스학부', NULL, 1);
	INSERT INTO exam VALUES(4, '컴퓨터공학과', '1호관', 2);
	INSERT INTO exam VALUES(5, '멀티미디어학과', '2호관', 2);
	INSERT INTO exam VALUES(6, '전자공학과', '3호관', 3);
	INSERT INTO exam VALUES(7, '기계공학과', '4호관', 3);
	COMMIT

	---------------------------------------------
	공과대학
		정보미디어학부
		   컴퓨터공학과
		   멀티미디어학과
		메카트로닉스학부
		   전자공학과
		   기계공학과

	select num,dname,loc,parent,level
	from exam
	start with num=1
	connect by parent = prior num;


//*****************************************
#SYS_CONNECT_BY_PATH(column, char) 함수
   계층적 쿼리에서만 유효하며, column의 절대 경로를 char로 지정한 문자로 분리하여 반환한다.
   select substr(max(sys_connect_by_path(name,',')),2) name
	from(
		select rownum rnum, name from insa where city='서울'
	)
	start with rnum=1
	connect by prior rnum= rnum-1;
   
//*****************************************
#트랜잭션(transaction)--면접 때 자주 나옴
   트랜잭션은 하나의 논리적 작업 단위로 수행되는 일련의 작업으로 테이블에 INSERT, UPDATE, DELETE 문으로 지시하고, COMMIT을 실행하면 모든 변경 사항이 영구히 데이터베이스에 저장되며, ROLLBACK은 마지막 COMMIT이나 ROLLBACK 이후의 변경 사항을 취소하고 데이터베이스를 원래의 상태로 되돌려 준다. 즉, 트랜잭션에서 데이터베이스를 변경하라는 명령이 COMMIT이고, COMMIT 전까지 변경된 것을 되돌리는 것이 ROLLBACK이다.
	1. COMMIT
	2. ROLLBACK
	3. SAVEPOINT
		3.1 트랜잭션 내의 한 시점을 표시한다.
		3.2 ROLLBACK TO SAVEPOINT 명령어로 표시 지점까지 ROLLBACK하는데 쓰인다.
		
	create table insa1 as select num, name, city from insa;
		insert into insa1 values(2000,'테스트','서울');
		select * from insa1;
		savepoint a;
		update insa1 set city='울산' where num=1001;
		select * from insa1;
		rollback to a;
		select * from insa;
//*****************************************
#SET TRANSACTION : 다양한 트랜잭션 속성을 지정 (관리자가 주로 다룬다. 내가 할일 별로 없음)
	1. set TRANSACTION read only; --select만 가능한 상태로 만든다.
	2. set TRANSACTION read write; --다양한 연산이 가능한 상태로 만든다.
//*****************************************
#SET AUTOCOMMIT
	sql>SHOW AUTOCOMMIT -- 상태확인
	AUTOCOMMIT OFF
	sql>SET AUTOCOMMIT ON
	sql>SHOW AUTOCOMMIT
	AUTOCOMMIT IMMEDIATE
	sql>SET AUTOCOMMIT 3
	sql>SHOW AUTOCOMMIT
	모든 3 DML문에 AUTOCOMMIT ON
	sql>SET AUTOCOMMIT OFF


//*****************************************
#LOCK 
	1. 현재의 트랜잭션이 사용하고 있는 데이터에 대해 다른 트랜잭션의 검색이나 변경을 막아 
		여러 트랜잭션이 동시에 같은 데이터를 사용 할 수 있도록 하는 것

	UPDATE insa1 SET city='seoul' where num=1001;
	select * from insa1;

	select * from insa1 for update wait 5; --5 초후 에러 발생
	
	lock table insa1 in exclusive mode; 
    -- EXCLUSIVE : 잠긴 테이블에 쿼리만 사용가능
    -- NOWAIT : 다른 사용자가 이미 LOCK 했어도 바로 자신에게 제어가 넘어옴
	delete from insa1;
	--다른 곳
	update insa1 set city='seoul' where num=1001; -- 커넥션1이 트랜잭션이 완료될 때 까지 대기
//*****************************************
#COMMIT이 되지 않는 상태 확인
	delete from insa;
	
#관리자 계정에서 확인 (여기서 출력된 사용자는 롤백이나 커밋을 하지 않은 상태이다.)
	SELECT s.inst_id inst,
		   s.sid||','||s.serial# sid,
		   s.username,
		   s.program,
		   s.status, 
		   s.machine,
		   s.service_name,
		   '_SYSSMU'||t.xidusn||'$' ROLLNAME,
		   --r.name rollname, 
		   t.used_ublk, 
		   ROUND(t.used_ublk * 8192 / 1024 / 1024, 2) used_bytes,
		   s.prev_sql_id,
		   s.sql_id
	  FROM gv$session s,
		   --v$rollname r,
		   gv$transaction t
	  WHERE s.saddr = t.ses_addr
	 ORDER BY used_ublk, machine;


//*****************************************
#PLSQL
	1. PL/SQL(Procedural Language extensions to SQL)은 프로그래밍언어의 특성을 가지는 SQL의 확장이며, 데이터 조작과 질의 문장은 PL/SQL의 절차적 코드 안에 포함된다. 또한 PL/SQL을 사용하면 SQL로 할 수 없는 절차적 작업이 가능하다. 여기에서 절차적이란 어떤 것이 어떻게 완료되는지 그 방법을 정확하게 코드에 기술 한다는 것을 의미한다.
	2. SQLGATE도 확인 가능
	3. 변수명과 속성명은 같은 이름을 사용할 수 없다.

#SET SERVEROUTPUT ON (PLSQL을 화면에 출력하는 법)
   SQLPLUS에서 "DBMS_OUTPUT.PUT_LINE(값)"으로 화면에 출력하기 위한 환경 변수
    - 참고 : ORACLE SQL Developer
     * DBMS_OUTPUT.PUT_LINE()을 이용하여 결과를 출력하기 위해서는 먼저 SET SERVEROUTPUT ON 를 실행
     * 메뉴 : 보기 - DBMS 출력 선택후 DBMS 출력 창의 에서 + 버튼을 누름 - plus icon to enable DBMS_OUTPUT for a connection.
     * DBMS_OUTPUT.PUT_LINE() 에 대한 결과가 한번은 잘 나오는데 두번째부터는 실행되지 않을 수 있다. 프로시져등을 만들때는 ORACLE SQL Developer가 편하지만 출력이 되지 않을 경우에는 sqlplus를 이용 한다.

# DBMS_OUTPUT.PUT_LINE('값');
	출력 후 라인 넘김

#DBMS_OUTPUT.PUT('값');
   출력 후 라인 넘기지 않음. DBMS_OUTPUT.NEW_LINE();을 만나야 화면에 표시 됨

-- DBMS_OUTPUT.NEW_LINE();
   라인 넘김

#PLSQL 기본구조
  DECLARE : 실행부에서 참조할 모든 변수, 상수, 커서, exception을 선언
  BEGIN : 데이터베이스의 데이터를 처리할 sql문과 pl/sql블록을 기술
  EXCEPTION : 실행부에서 에러가 발생했을때 수행될 문장을 기술
  END : pl/sql블록의 끝

	[DECLARE]
		[변수선언;]
	BEGIN
		  구문;
		  [EXCEPTION]
	END;
	(/) :sqlplus 에서 PLSQL문의 종료를 알림
	
	declare
    vname varchar2(30);
    vpay NUMBER;
	BEGIN
		--select name, basicpay, into vanme, vpay from insa; --에러 발생 데이터가 두개 이상이므로
		select name, basicpay into vname, vpay from insa where num = 1001;
		DBMS_OUTPUT.PUT_LINE(vname || ' ' || vpay); --(DBMS_OUTPUT = 패키지) =>출력해서 보는법
	END;
	/

#PL/SQL 기본 스칼라 데이터 형
	1. VARCHAR2(n) : 변수길이 문자 데이터에 대한 기본형은 32767Byte까지 디폴트 크기는 없다.
	2. NUMBER(p,s) : 고정과 유동포인트 숫자에 대한 기본형 소수점 이상 p자리, 소수점 이하 s자리
	3. DATE : 날짜와 시간에 대한 기본형, date값은 지정 이후의 초 단위로 날에 대한 시간을 포함합니다. 날짜의 범위는 bc4712년 1월1일 부터 ad9999년 12월 31일 사이
	4. CHAR(n) : 고정 길이 문자에 대한 기본형은 32767Byte까지. 디폴트길이는 1
	5. LONG : 고정길이 문자에 대한 기본형은 32767Byte까지 long은 최대 2147483647Byte까지
	6. LONG RAW : 이진 데이터와 바이트 문자열에 대한 기본형은 32767Byte까지 long raw는 PL/SQL에 의해 해석되지 않음
	7. BOOLEAN : true, false, null중 하나의 값
	8. BINARY_INTEGER : -2147483647~2147483637사이의 정수
	9. PLS_INTEGER : -2147483647~2147483637사이의 정수에 대한 기본형 적은 기억장치를 필요로함

#PLSQL 변수 선언
	1. 변수 자료형 [:=초기치];
	2. 대입문 => 변수 := 값;

#PL/SQL 에서 일반적인 SELECT 문 형식
	1. SELECT 컬럼명, 컬럼명 INTO 변수명, 변수명 FROM 테이블명 WHERE 조건;
	// 바로 출력할 수 없고 변수에 담아서 처리해야한다.

#%TYPE
  - 테이블 컬럼 자료형을 참조하는 자료형

#%ROWTYPE
   - 테이블 스키마와 같은 구조체변수

#PLSQL 사용자 정의 구조체 변수
		TYPE 레코드명 IS RECORD 
		(
		   field_name1 datatype [[NOT NULL] { := | DEFAULT} expr] 
		   ,field_name2 datatype [[NOT NULL] { := | DEFAULT} expr] 
		   .................. 
		);


#바인드 변수
   바인드 변수는 호스트 환경에서 선언된 변수이며, PL/SQL 프로그램의 내부나 외부에서 전달하기 위해 사용한다.
   VAR[IABLE] 변수는 session이 종료될 때 까지 사용 가능하다.
   호스트 변수를 참조하기 위해 선언된 PL/SQL변수와 호스트 변수를 구별하기 위해 콜론(:)으로 참조 접두어를 기술하여야 한다.

    1) VARIABLE
       VAR[IABLE] [ variable [ NUMBER |  CHAR (n) | VARCHAR2 (n)] ]
		like ... variable name varchar2(30)
				variable score number
    2) EXECUTE
       선언된 변수를 사용할 때 사용

    3) PRINT
       환경에서 바인드 변수의 현재 값을 출력하기 위해 PRINT 명령을 사용한다.


# IF 조건 THEN ~ ELSIF 조건 THEN ~ ELSE ~ END IF 문
	1. TRUE면 THEN과 ELSE사이의 문장을 수행하고 FALSE나 NULL이면 ELSE와 END IF사이의 문장을 수행한다.
	declare
    a number := 10;
	begin
		if mod(a,6)=0 then
			dbms_output.put_line(a || ' 2 또는 3의 배수');
		elsif mod(a,2)=0 then
			dbms_output.put_line(a || ' 2의 배수');
		elsif mod(a,3)=0 then
			dbms_output.put_line(a || ' 3의 배수');
		else
			dbms_output.put_line(a || ' 2 또는 3의 배수가 X');
		END if;
	end;

#무한루프
	DECLARE
    N NUMBER := 0;
    S NUMBER := 0;
	BEGIN
		LOOP
			N := N+1;
			S := S+N;
			EXIT WHEN N=100;
		END LOOP;
		DBMS_OUTPUT.PUT_LINE('결과 : ' || S);
	END;
	/

# WHILE 조건 LOOP ~ END LOOP
	1. 제어 조건이 TRUE인 동안만 일련의 문장을 반복하기 위해 WHILE LOOP 문장을 사용한다.
	
	EXIT [WHEN condition];
	EXIT 문을 이용하면 END LOOP 문 다음 문으로 제어를 보내기 때문에 루프를 종료할 수 있다. 
	
	LOOP ~ END LOOP
    반복문으로 "EXIT WHEN 조건"이 실행문 앞과 뒤 어느 곳이나 위치할 수 있으며 "EXIT WHEN 조건"에서 조건을 만족하면 빠져 나간다.
	DECLARE
		N NUMBER := 1;
	BEGIN
		WHILE N<=9 LOOP
			DBMS_OUTPUT.PUT_LINE('7*'||N||'='||7*N);
			N := N+1;
		END LOOP;
	END;

#FOR ~ LOOP 문
	1.  FOR 변수 IN [REVERSE] 시작수 .. 끝낼수 LOOP
		  실행문; 
		END LOOP; 

		FOR 에서 사용되는 변수는 자동 선언되므로 따로 선언하지 않아도 됨. 증가분이 없다.
		"시작수"에서 1씩 증가하여 "끝날 수"가 될 때까지 반복 수행하며, FOR문에 사용되는 변수는 자동 선언되므로 따로 선언할 필요가 없다.
		REVERSE : "끝날수"에서 "시작수"까지 반복함으로써 인덱스가 1씩 감소되도록 한다.
	
		DECLARE
			S NUMBER := 0;
		BEGIN
			FOR N IN 1..100 LOOP
				S := S+N;
			END LOOP;
			DBMS_OUTPUT.PUT_LINE('결과 : ' || S);
		END;
		/

		DECLARE
		BEGIN
			FOR N IN REVERSE 1..10 LOOP --아무것도 안나옴 왜냐 REVERSE 할때는 숫자를 거꾸로 쓰면안된다 1..10으로 써야한다.
				DBMS_OUTPUT.PUT_LINE(N);
			END LOOP;
		END;
		/
		
#FOR 를 이용한 SELECT 문
	1.  FOR 레코드이름 IN (SELECT 컬럼 FROM 테이블 [WHERE 조건])  LOOP
		실행문; 
		END LOOP;
		DECLARE
		BEGIN 
			FOR REC IN (SELECT NAME, BASICPAY FROM INSA) LOOP
				DBMS_OUTPUT.PUT_LINE(REC.NAME || ' ' || REC.BASICPAY);
			END LOOP;
		END;
		/
		

//*****************************************
#프로시져(Stored Procedure) =>매우 유용 : 자주 사용되는 쿼리를 데이터베이스에 저장해 놓고 불러올 수 있는 기능
   - PL/SQL에서 가장 대표적인 구조인 스토어드 프로시져는 개발자가 자주 실행해야 하는 업무 흐름(sql)을 미리 작성하여 데이터베이스 내에 저장해 두었다가 필요할 때마다 호출하여 실행할 수 있다.
   - 패키지 내에서 프로시져는 중복 정의가 가능하다.

#프로시져 생성 권한 부여
	- 사용자에게 프로시져를 만들 수 있는 권한이 없는 경우 권한 설정(RESOURCE 롤에 기본적으로 프로시져를 만들수있는 권한 있음)
	- SYS 계정
	- 사용자에게 프로시져를 만들 수 있는 권한이 없는 경우 부여
	GRANT CREATE PROCEDURE TO 사용자명;
	- 사용자 계정
	- 부여된 권한 확인
	SELECT * FROM USER_SYS_PRIVS;
	
#상호참조 관계 확인 : 테이블의 프로시져, 함수, 패키지 등 상호 참조되는 관계 확인
	SELECT * FROM user_dependencies;
	1. 테이블이 삭제된다고 연관된 프로시져가 삭제 되는것은 아니다.
	2. 테이블이 삭제된 상태에서 연관된 프로시져를 실행 하면 오류가 발생 한다.
		   
#프로시져 목록 확인
	SELECT object_name FROM user_procedures;
	SELECT * FROM user_procedures;
	
#소스확인
	SELECT text FROM user_source;
	
#프로시져 작성
	CREATE [OR REPLACE] PROCEDURE 프로시져이름
	[(
	   매개변수 IN 자료형,   -- 입력용, 인수는 자료형크기를 지정할 수 없다.
	   매개변수 OUT 자료형,	-- 출력용
	   매개변수 IN OUT 자료형	-- 입/출력용
	)]
	IS 
	   [변수의 선언]
	BEGIN
	   .............
	   [EXCEPTION]
	   .............
	END;

	 IN 파라미터 : 호출자에 의해 프로시져로 전달되는 파라미터이며, '읽기' 전용의 값으로 프로시져는 이 파라미터의 값을 변경할 수 없다.(디폴트 모드)
	 OUT 파라미터 : 프로시져에서 값을 변경할 수 있고, '쓰기' 기능으로 프로시져가 정보를 호출자에게 돌려주는 기능이다. OUT 파라미터는 디폴트값을 지정할 수 없다.
	 IN OUT 파라미터 : 프로시져가 읽고 쓰는 작업을 동시에 할 수 있는 파라미터이다.

	#주의
    1) 프로시져 안에서는 INSERT, UPDATE, DELETE문을 사용하는 경우 자동 커밋이 되지 않으므로 COMMIT; 구문을 추가 해야 한다.
    2) 프로시져에서 INSERT 문을 2개 이상 사용하여 데이터를 추가하는 경우 첫번째 INSERT문은 성공하고 두번째 INSERT 에서 추가를 실패하면 첫번째 INSERT는 자동으로 ROLLBACK 된다.
	
	SELECT * FROM SEQ;
	
	CREATE SEQUENCE SEQ1 INCREMENT BY 1 START WITH 1;

	CREATE TABLE test9 (
		NUM NUMBER PRIMARY KEY
		,NAME VARCHAR2(30) NOT NULL
		,SCORE NUMBER(3) NOT NULL
		,GRADE VARCHAR2(10) NOT NULL
	);

	CREATE OR REPLACE PROCEDURE pInsertTest
	IS
	BEGIN
		INSERT INTO test9(num,name,score,grade) VALUES (seq1.NEXTVAL, '홍길동', 85, '우');
		COMMIT;
	END;
	/


#프로시져 실행
	1. EXECUTE
		EXEC[UTE] 프로시져이름[(인수, 인수)];

	2. CALL
		CALL 프로시져이름([인수, 인수]);

	   - CALL에서 사용되는 인수를 지정하는 세가지
		 CALL my_procedure(3, 4)
		 CALL my_procedure(arg1 =>3, arg2 =>4)
		 CALL my_procedure(3, arg2 => 4) 

#프로시져 삭제
	1. DROP PROCEDURE 프로시져이름;

#프로시져 수정
	CREATE OR REPLACE PROCEDURE PINSERTTEST(
    pname IN varchar2 --파라미터에서는 절대 크기를 명시하지 않는다.
    , pscore in test9.score%TYPE --number로 써도 상관은 없다.
	)
	is
		vgrade varchar2(10);
	begin
		if pscore >= 90 then 
			vgrade := '수';
		elsif pscore >= 80 then
			vgrade := '우';
		elsif pscore >= 70 then 
			vgrade := '미';
		elsif pscore >= 60 then
			vgrade := '양';
		else 
			vgrade := '가';
		end if;
		
		insert into test9(num,name,score,grade) values (seq1.nextval,pname,pscore,vgrade);
		commit;
	end;
	/

#예외 처리
-- RAISE_APPLICATION_ERROR(error_number, error_message);
  - 표준화되지 않은 에러 코드와 에러 메시지를 리턴하기 위해 RAISE_APPLICATION_ERROR() 프로시져 사용
  - RAISE_APPLICATION_ERROR를 만나면 처리를 중단하며, 자바등의 어플리케이션에는 에러를 던져주어 어플리케이션에서는 SQLException을 이용해서 에러를 확인할 수가 있다.
  - error_message : 출력할 메시지
  - error_number : -20999~-20000사이의 수 사용

	CREATE OR REPLACE PROCEDURE pInsertTest(
    pname IN varchar2 --파라미터에서는 절대 크기를 명시하지 않는다.
    , pscore in test9.score%TYPE --number로 써도 상관은 없다.
	)
	is
		vgrade varchar2(10);
	begin
		if pscore <0 or pscore >100 then
			RAISE_APPLICATION_ERROR(-20001, '점수는 0~100 사이만 가능합니다.');
		end if;
		
		if pscore >= 90 then 
			vgrade := '수';
		elsif pscore >= 80 then
			vgrade := '우';
		elsif pscore >= 70 then 
			vgrade := '미';
		elsif pscore >= 60 then
			vgrade := '양';
		else 
			vgrade := '가';
		end if;
		
		insert into test9(num,name,score,grade) values (seq1.nextval,pname,pscore,vgrade);
		commit;
	end;
	/

exec pinserttest('나나나',170);
select * from test9;

#프로시져 예제 
	1. update
		CREATE OR REPLACE PROCEDURE pUpdateTest(
			pnum in test9.num%type
			, pname IN varchar2 --파라미터에서는 절대 크기를 명시하지 않는다.
			, pscore in test9.score%TYPE --number로 써도 상관은 없다.
		)
		is
			vgrade varchar2(10);
		begin
			if pscore <0 or pscore >100 then
				RAISE_APPLICATION_ERROR(-20001, '점수는 0~100 사이만 가능합니다.');
			end if;
			
			if pscore >= 90 then 
				vgrade := '수';
			elsif pscore >= 80 then
				vgrade := '우';
			elsif pscore >= 70 then 
				vgrade := '미';
			elsif pscore >= 60 then
				vgrade := '양';
			else 
				vgrade := '가';
			end if;
			
			
			update test9 set name = pname, score = pscore, grade = vgrade where num = pnum; 
			commit;
		end;
		/

		EXEC pupdateTest(1,'도도도',90);
		select * from test9;
	
	2. delete
		CREATE OR REPLACE PROCEDURE pDeleteTest(
			pnum in test9.num%type
		)
		is
		begin
			delete from test9 where num = pnum;
			commit;
		end;
		/

		exec pDeleteTest(1);
		select * from test9;	
		
	3. 테이블의 데이터 중 하나의 레코드를 출력하는 프로시져
	create or replace procedure pSelectTest
	(
		pnum in number
	)
	is
		TYPE MYTYPE IS RECORD
		(
			num test9.num%type,
			name test9.name%type,
			score test9.score%TYPE,
			grade test9.grade%TYPE
		);
		
		rec MYTYPE;
		
	begin
		select num, name, score, grade into rec.num, rec.name, rec.score, rec.grade from test9 where num = pnum;
		DBMS_OUTPUT.PUT_LINE(rec.num || ':' || rec.name || ':' || rec.score || ':' || rec.grade);
	end;
	/
	select * from test9;
	exec pSelectTest(2);
	4. 전체 행을 받아오는 방법
	create or replace procedure pSeletAllTest
	is
	begin
		for abc in (select * from test9) loop
		   DBMS_OUTPUT.PUT_LINE(abc.num || ':' || abc.name || ':' || abc.score || ':' || abc.grade); 
		end loop;
	end;
	/
	select * from tEst9;
	exec pSeletAllTest;
	
	4. 프로시저를 이용해서 여러 테이블을 한번에 처리 가능하다. (참조관계일 경우 유용) 
	5. View를 이용해서도 가능하다.
	6. 행 하나를 담을 수 있는 변수 [변수명(ex:vrow) 테이블명%rowtype]
	
#프로시져 파라미터
	1. IN 파라미터 : 디폴트 값. 프로시져가 호출될 때 같이 넘어오는 데이터를 저장할 파라미터 (읽기 전용)
					값 변경할 수 없다.
					
	2. OUT 파라미터 : 프로시져를 호출한 곳으로 결과 데이터를 리턴해줄 목적으로 사용되는 파라미터 (쓰기 기능)
		create or replace procedure pSelectOutTest
		(
			pnum IN number,
			pname out varchar2,
			pscore out number
		)
		is
		begin
			-- pnum := 10; -- 에러. 읽기 전용이므로
			-- pname := 'a'; -- 가능.
			select name, score into pname, pscore from sangkeun where num = pnum;
		end;
		/
		
		호출시 pSelectOutTest(1,pname값을 받을 변수, pscore값을 받을 변수);
		
	3. IN/OUT 파라미터 : IN과 OUT의 읽기,쓰기 기능을 가능하게 하는 파라미터
		create or replace procedure pinOutTest
		(
			pnum in out number
		)
		is
			n number := 1;
		begin
			for a in 1..pnum loop
				n := n * 2;
			end loop;
				pnum := n;
		end;
		/

		

#Stored Function(사용자 함수)
	1. 스토어드 함수는 시스템 함수처럼 쿼리에서 호출하거나 저장 프로시저처럼 EXECUTE 문을 통해 실행할 수 있다.
	2. 패키지 내에서 Function은 중복 정의가 가능하다.


#사용자 함수 형식(인수나 RETURN 에서는 자료형의 크기를 명시하지 않는다.)
		CREATE [OR REPLACE] FUNCTION 함수이름
		[(
		   매개변수1 IN 자료형,
		   매개변수2 IN 자료형
		)]
		RETURN datatype;
		IS 
		   [변수의 선언]
		BEGIN
		   .............
			RETURN (값);
		   [EXCEPTION]
		   .............
		END;
		/
	
		create or replace function fnGrade
		(
			pScore NUMBER
		)
		return number
		is n number(2,1);
		begin
			if pscore>=95 then n:=4.5;
			elsif pscore>=90 then n:=4.0;
			elsif pscore>=85 then n:=3.5;
			elsif pscore>=80 then n:=3.0;
			elsif pscore>=75 then n:=2.5;
			elsif pscore>=70 then n:=2.0;
			elsif pscore>=65 then n:=1.5;
			elsif pscore>=60 then n:=1.0;
			else n:=0.0;
			end if;
			return n;
		end;
		/
#함수 목록 확인
SELECT * FROM user_procedures;

----------------------------------------------
-- EXECUTE 또는 CALL를 이용한 함수 실행
 1. EXECUTE
    EXEC :바인딩변수 := 함수명([인수, 인수]);
    PRINT :바인딩변수

 2. CALL
    CALL 함수명([인수, 인수]) INTO :바인딩변수;
    PRINT :바인딩변수

//*****************************************
#커서(Cursor)
    하나의 레코드가 아닌 여러 레코드로 구성된 작업영역에서 SQL문을 실행하고 그 과정에 생긴 정보를 저장하기 위해서 CURSOR를 사용
	//select * from insa => 커서 라는 하나의 정보에 60가지가 존재한다.
	
		1. 암시적인 커서	
			암시적인 커서는 오라클이나 PL/SQL실행 메커니즘에 의해 처리되는 SQL문장이 처리되는 곳에 대한 익명의 address로 오라클 데이터베이스에서 실행되는 모든 SQL문장은 암시적인 커서이며, 암시적인 커서 속성이 사용될 수 있다.
			--암시적 커서의 속성
			  SQL%ROWCOUNT : 해당 SQL 문에 영향을 받는 행의 수 --commit 전에 사용해야한다.
			    vcount := SQL%ROWCOUNT;
				dbms_output.put_line(vcount||'개 삭제');
				
			  SQL%FOUND : 해당 SQL 영향을 받는 행의 수가 1개 이상일 경우 TRUE
			  
			  SQL%NOTFOUND : 해당 SQL 문에 영향을 받는 행의 수가 없을 경우 TRUE
				if sql%notfound then raise_application_error(-20001,'데이터가 없습니다.');
				
			  SQL%ISOPEN : 항상 FALSE, 암시적 커서가 열려 있는지의 여부 검색

		2. 명시적(explicit) 커서
			명시적 커서는 프로그래머에 의해 선언되며 이름이 있는 커서로 여러 row를 다룰 수 있다.
			-- 작업 순서
			   CURSOR 선언 → 커서 OPEN → FETCH → 커서 CLOSE
			-- 커서 선언
			  CURSOR 커서이름 IS [SELECT 문];
			  실행하고자 하는 SELECT문을 작성 한다.
			-- 커서 OPEN
			   OPEN 커서이름;
  			   OPEN은 커서에서 선언된 SELECT문의 실행을 의미 한다.		 
			-- FETCH
				LOOP
					FETCH 커서이름 INTO variable1, variable2 ;
					EXIT WHEN [조건];
				END LOOP;
				 OPEN된 SELECT 문에 의해 검색된 하나의 행 정보를 읽어 OUT 변수에 대입한다. 
				 만약 리턴 되는 결과가 여러 개인 경우 LOOP ~ END LOOP와 같은 반복문을 이용하여 마지막 행이 읽혀질 때까지 계속 읽게 된다.
			-- 커서 CLOSE
			   CLOSE 커서이름;
			   선언된 SELECT문의 선언을 해제 한다.

				 declare
					vname insa.name%type;
					vpay insa.basicpay%type;
					--커서 선언
					cursor insa_list is select name, basicpay from insa;
					
				begin
						--커서 오픈
						open insa_list;
						loop
							--커서 패치
							fetch insa_list into vname, vpay;
							exit when insa_list%notfound;
							dbms_output.put_line(vname||':'||vpay);
						end loop;
						--커서 종료
						close insa_list;
				end;/
		3. FOR 문에서 커서 사용(Cursor FOR Loops) => 2번 보다 더 쉬운 방법(편함)
		   FOR 문을 사용하면 커서의 OPEN, FETCH, CLOSE가 자동 발생하므로 따로 기술할 필요가 없고 레코드 이름도 자동 선언되므로 따로 선언할 필요가 없다.
			-- 형식
			   FOR 레코드이름 IN 커서이름 LOOP
					  문장;
					  ......
			  END LOOP;
			  
		4. WHERE CURRENT OF
		   FETCH문에 의해 가장 최근에 처리된 행을 참조하기 위해서 "WHERE CURRENT OF 커서이름 " 절로 DELETE나 UPDATE문 작성이 가능하다. 이 절을 사용하기 위해서는 참조하는 커서가 있어야 하며, FOR UPDATE절이 커서 선언 query문장 안에 있어야 한다.


//*****************************************
#SYS_REFCURSOR (매우 중요) (프로시저에서 out 파라미터로 자주 쓰인다. 테이블 내 여러 컬럼들을 한번에 담을 수 있음)
	- REF CURSOR : 9i 이전
	- SYS_REFCURSOR : 9i 이후(현재 version)
	- 테이블의 여러 로우를 반복적으로 조회하기 위해 레퍼런스 커서(reference cursor)를 사용한다.
	- for문장 사용 못함, fetch,LOOP 사용해야함
	create or replace procedure pSelectInsa
	(
		pResult out sys_refcursor
	)
	is
	begin
		-- 커서 생성 및 동시에 open 하고 결과를 pResult에 저장
		OPEN pResult for 
			select name, basicpay, city from insa;
	end;
	/
	
//*****************************************
#동적 SQL
   동적 쿼리는 프로시져등에서 인자로 받은 변수를 조합하여 쿼리문을 생성하여 사용하는 경우를 말한다.
   예를 들어 블로그에서 사용자별 게시판 테이블을 만들 때.

	주의
     RESOURCE 롤은 테이블을 생성할 수 있지만 동적 SQL을 이용하여 테이블을 생성할수 는 없다. 동적 SQL로 테이블을 생성하기 위해서는 CREATE TABLE 권한을 설정 해 주어야 한다.

	SYS 계정
	  cmd>sqlplus sys/"암호" AS sysdba;
	  sql>GRANT CREATE TABLE TO 사용자;

	EXECUTE IMMEDIATE dynamic_sql_string
			 [INTO {define_var1 [, define_var2] ... | plsql_record }]
			 [USING [IN | OUT | IN OUT] bind_arg1 [,
			 [IN | OUT | IN OUT] bind_arg2] ...]; 

	//기존 create문 만으로도 생성은 가능하나 제약조건을 복사할 수 없다.
		create or replace procedure boardtablecreate
		(
			pTableName varchar2
		)
		is
			s varchar2(4000);
		begin
			s := 'create table ' || pTableName;
			s := s || '(num number primary key,';
			s := s || 'name varchar2(30) not null,';
			s := s || 'subject varchar2(255) not null,';
			s := s || 'content varchar2(4000) not null,';
			s := s || 'hitCount number default 0,';
			s := s || 'created date default sysdate)';
			
			for t in (select tname from tab where tname = upper(pTableName)) LOOP
				Execute immediate 'drop table ' || pTableName || ' purge';
				DBMS_OUTPUT.put_line('테이블 삭제...');
				exit;
			end loop;
			
			Execute immediate s;
			DBMS_OUTPUT.put_line(pTableName || '테이블 생성...');
			
			for t in (select sequence_name from seq where sequence_name = upper(pTableName||'_seq')) LOOP
				Execute immediate 'drop sequence ' || pTableName || '_seq';
				DBMS_OUTPUT.put_line('시퀀스 삭제...');
				exit;
			end loop;
			Execute immediate ' create sequence ' || pTableName || '_seq'; 
			DBMS_OUTPUT.put_line('시퀀스 생성...');
		end;
		/

		exec boardtablecreate('bbs');

		select * from tab;
		select * from seq;

		create or replace procedure boardtablecreate
		(
			pTableName varchar2
		)
		is
			s varchar2(4000);
		begin
			s := 'create table ' || pTableName;
			s := s || '(num number primary key,';
			s := s || 'name varchar2(30) not null,';
			s := s || 'subject varchar2(255) not null,';
			s := s || 'content varchar2(4000) not null,';
			s := s || 'hitCount number default 0,';
			s := s || 'created date default sysdate)';
			
			for t in (select tname from tab where tname = upper(pTableName)) LOOP
				Execute immediate 'drop table ' || pTableName || ' purge';
				DBMS_OUTPUT.put_line('테이블 삭제...');
				exit;
			end loop;
			
			Execute immediate s;
			DBMS_OUTPUT.put_line(pTableName || '테이블 생성...');
			
			for t in (select sequence_name from seq where sequence_name = upper(pTableName||'_seq')) LOOP
				Execute immediate 'drop sequence ' || pTableName || '_seq';
				DBMS_OUTPUT.put_line('시퀀스 삭제...');
				exit;
			end loop;
			Execute immediate ' create sequence ' || pTableName || '_seq'; 
			DBMS_OUTPUT.put_line('시퀀스 생성...');
		end;
		/

		exec boardtablecreate('bbs');

		select * from tab;
		select * from seq;
		
		
		create or replace procedure boardtableinsert
		(
			pTableName varchar2,
			pName varchar2,
			pSubject varchar2,
			pContent varchar2
		)
		is
			s varchar2(4000);
		begin
			s := 'insert into ' || pTableName|| '(num,name,subject,content) ';
			s := s || 'values ('||pTableName||'_seq.nextval,:1, :2, :3)';
			
			Execute immediate s using pName, pSubject, pContent;
			commit;
			DBMS_OUTPUT.put_line(pTableName || '데이터 추가...');
		end;
		/

		exec boardtableinsert('bbs','sangkeun1','sangkeun2','sangkeun3');

		select * from bbs;
		select * from tab;
		select * from seq;

		select * from cols where table_name='BBS';

		
		//*****************************************
#트리거(TRIGGER)
	TRIGGER란 DML 작업 즉, INSERT, DELETE, UPDATE 작업이 일어날 때 자동으로 실행되는 객체로 특히 이런 TRIGGER를 DML TRIGGER라 한다. TRIGGER는 데이터의 무결성 뿐만 아니라 다음과 같은 작업에도 사용된다.
		-자동으로 파생된 열 값 생성 
		-잘못된 트랜잭션 방지 
		-복잡한 보안 권한 강제 수행 
		-분산 데이터베이스의 노드 상에서 참조 무결성 강제 수행 
		-복잡한 업무 규칙 강제 수행 
		-투명한 이벤트 로깅 제공 
		-복잡한 감사 제공 
		-동기 테이블 복제 유지 관리 
		-테이블 액세스 통계 수집 
	트리거 내에서는 COMMIT, ROLLBACK 문을 사용할 수 없다.

#문장 트리거(문장당 한번만 실행) (전체 테이블 삭제로 인해 삭제를 3번해도 한번만 들어간다.)
    트리거가 설정된 테이블에 트리거 이벤트가 발생하면 많은 행에 대해 변경 작업이 발생하더라도 오직 한번만 트리거를 발생시키는 방법
	-- 문장 트리거
	CREATE TABLE EXAM1 (
		id varchar2(50) primary key,
		name varchar2(50) not null    
	);

	create table exam2(
		memo varchar2(100),
		created date default sysdate
	);

	----------------------------------------------------------------
	create or replace trigger triExam after
	insert or update or delete on exam1
	begin
		if inserting then 
			insert into exam2(memo) values ('추가');
		elsif updating then
			insert into exam2(memo) values ('수정'); 
		elsif deleting then
			insert into exam2(memo) values ('삭제');
		end if;
	end;
	/
	insert into exam1(id,name) values('dlatkdrms301','sangkeun');
	insert into exam1(id,name) values('dlatkdrms302','sangkeun');
	insert into exam1(id,name) values('dlatkdrms303','sangkeun');
	delete from exam1;
	commit;
	update exam1 set name='sang' where id='dlatkdrms301';

	select * from exam2;
	
	exam1 테이블에 평일과 9~18시에만 작업이 가능한 트리거 만들기
	create or replace trigger triTime before
	insert or update or delete on exam1
	begin
		if to_char(sysdate, 'd') in (7,1) or to_char(sysdate, 'HH24')<9 or to_char(sysdate, 'HH24') > 18 then
			raise_application_error(-20001,'일은 근무시간에 해라');
		end if;
	end;
	/
#행 트리거
    조건을 만족하는 여러 개의 행에 대해 트리거를 반복적으로 여러 번 수행하는 방법으로 [FOR EACH ROW WHEN 조건]절 정의된다.
	

#형식
	CREATE [OR REPLACE] TRIGGER 트리거명 (BEFORE | AFTER) (BEFORE 명령전에 실행 | AFTER 명령후에 실행)
	  이벤트-1 [OR 이벤트-2 OR 이벤트-3] ON 테이블명
	  [FOR EACH ROW [WHEN TRIGGER 조건]]
	  DECLARE
		선언문
	  BEGIN
		PL/SQL 코드
	  END;
		create table score1 (
		hak varchar2(30) primary key,
		name varchar2(30) not null,
		kor number(3) not null,
		eng number(3) not null,
		mat number(3) not null
		)
		create table score2(
		hak varchar2(30) primary key,
		tot number(3) not null,
		ave number(5,1) not null,
		constraint fk_score2_hak foreign key(hak) references score1(hak)
		);

		create table score3 (
		hak varchar2(30) primary key,
		kor number(3,1) not null,
		eng number(3,1) not null,
		mat number(3,1) not null,
		constraint fk_score3_hak foreign key(hak) references score1(hak)
		);
	create or replace trigger triInsertScore
	after insert on score1
	for each row
	declare
		vtot number(3);
		vave number(5,1);
	begin
		vtot := :NEW.kor + :NEW.eng + :NEW.mat; --insert된 레코드를 :NEW로 표시한다. :OLD
		vave := vtot / 3;
		
		insert into score2(hak, tot, ave) values (:new.hak, vtot, vave);
		insert into score3(hak, kor, eng, mat) values(:new.hak,fnGrade(:NEW.kor),fnGrade(:NEW.eng),fnGrade(:NEW.mat));
	end;
	/

	insert into score1(hak, name, kor, eng, mat) values ('1','aaa',80,85,75);

	select * from score1;
	select * from score2;
	select * from score3;
	
#이벤트
     INSERT, UPDATE, DELETE

    -- BEFORE : 구문을 실행하기 전에 트리거를 시작
    -- AFTER : 구문을 실행한 후에 트리거를 시작
    -- FOR EACH ROW : 행 트리거임을 알림
    -- WHEN 조건 : 사용자의 트리거 이벤트 중에 조건에 만족하는 데이터만 트리거 한다.
    -- REFERENCING : 영향 받는 행의 값을 참조
    -- :OLD : 참조 전 열의 값(INSERT : 입력 전 자료, UPDATE : 수정 전 자료, DELETE : 삭제할 자료)
    -- :NEW : 참조 후 열의 값(INSERT : 입력할 자료, UPDATE : 수정할 자료)

      ROW 트리거에서 컬럼의 실제 데이터 값을 제어하는데 사용하는 연산자는 :OLD와 :NEW 의사 레코드이다. 이 연산자와 함께 컬럼 명을 함께 기술한다. 예를 들어, 컬럼명이 sal이라고 하면, 변경전의 값은 :OLD.sal이고 변경 후의 값은 :NEW.sal 처럼 표기한다.

      문장 트리거에서는 :NEW, :OLD 를 참조 할 수 없다.

#트리거 상태 확인
	SQL>SELECT trigger_name, trigger_type, table_name FROM user_triggers;

#트리거 삭제
	DROP TRIGGER 트리거명;

-- SYS 계정
-- 사용자에게 트리거를 만들 수 있는 권한 부여
GRANT CREATE TRIGGER TO 사용자명;

-- 사용자 계정
-- 부여된 권한 확인
SELECT * FROM USER_SYS_PRIVS;


//*****************************************
#문장 트리거
  트리거가 설정된 테이블에 트리거 이벤트가 발생하면 많은 행에 대해 변경 작업이 발생하더라도 오직 한번만 트리거를 발생시키는 방법

---------------------------------------------
#트리거 확인
SELECT trigger_name, trigger_type, table_name
            FROM user_triggers;

SELECT TEXT FROM user_source;


//*****************************************
#행트리거
    조건을 만족하는 여러 개의 행에 대해 트리거를 반복적으로 여러 번 수행하는 방법으로 [FOR EACH ROW WHEN 조건]절 정의된다.
	1. :NEW -> 참조후 열의 값
     -- INSERT에 의해 새로 추가된(할) 레코드
     -- UPDATE에 의해 수정된(할) 레코드
	2. :OLD -> 참조전 열의 값
     -- UPDATE에 의해 수정되기 전 레코드
     -- DELETE에 의해 삭제된(할) 레코드

	다음과 같은 방법을 이용하여 관련된 트리거는 하나의 트리거로 작성 할 수 있다.
		CREATE OR REPLACE TRIGGER 트리거이름
		AFTER[또는 BEFORE] DELETE OR INSERT OR UPDATE ON 테이블명
		FOR EACH ROW
		[
		DECLARE
		   변수 타입;
		]
		BEGIN
		  IF INSERTING THEN 
			-- 추가할 때 
		  ELSIF UPDATING THEN
			-- 수정할 때 
		  ELSIF DELETING THEN
			-- 삭제할 때 
		  END IF;
		END;
		/
		
		create or replace trigger insTrg_lpgo
		after insert on 입고
		for each row
		begin
			update 상품 set 재고수량 = 재고수량 + :NEW.입고수량 where 상품코드 = :NEW.상품코드;
		end;
		/
		
//*****************************************
#패키지
	1. 타입, 프로그램객체, 프로시져, 함수등을 논리적으로 묶어 놓은것
	2. 패키지 내에서 프로시져, 함수등은 중복 정의가 가능하다.

#패키지 작성 방법
   1. 패키지 명세 (선언부만)
		 CREATE OR REPLACE PACKAGE 패키지명 IS
		FUNCTION 함수명([인수]) RETURN 리턴타입;
		PROCEDURE 프로시져명([인수]);
		   :
		 END 패키지명;
		 /

   2. 패키지 몸체 구현
		 CREATE OR REPLACE PACKAGE BODY 패키지명 IS
		FUNCTION 함수명([인수]) RETURN 리턴타입
		IS
		   [변수선언]
		BEGIN
		   함수몸체구현
		   RETURN 리턴값;
		END;

		PROCEDURE 프로시져명([인수])
		IS
		   [변수선언]
		BEGIN
		   프로시져몸체구현
		END;
		   :
		END 패키지명;
		/
		
		-- 패키지 명세
		create or replace package pack_insa is
			function fnGender (SSN IN VARCHAR2) RETURN VARCHAR2;
			procedure list_insa(pNum in number);
			procedure list_insa(pName in varchar2);
		end pack_insa ;
		/

		drop package Pack_insa;
		-- 패키지 몸체 구현
		create or replace package body pack_insa is
			function fnGender(SSN in varchar2)
			return varchar2
			is
				s varchar2(6) := '여자';
			begin 
				if mod(substr(ssn,8,1),2)= 1 then s := '남자';
				end if;
				return s;
			end;
			
			procedure list_insa(pNum in number)
			is
				vName varchar2(30);
				vSsn varchar2(30);
				vGender varchar2(6);
			begin
				select name, ssn, fnGender(ssn) gender into vName, vSsn, vGender from insa where num = pNum;
				DBMS_OUTPUT.PUT_LINE(vName||':'||vSsn||':'||vGender);
			end;
			
			procedure list_insa(pName in varchar2)
			is
				cursor cur is
				select name, ssn, fnGender(ssn) gender from insa where (name, pName) >= 1;
			begin
				for rec in cur loop
					DBMS_OUTPUT.PUT_LINE(rec.name||':'||rec.ssn||':'||rec.gender);
				end loop;
			end;
		end pack_insa;
		/

		EXEC pack_insa.list_insa('김');
		
//*****************************************
#예외처리
  - PL/SQL 을 실행 하는 동안에 발생하는 에러 처리

  - 예외 트랩(trap)
     만일 예외가 블록의 실행 가능한 섹션에서 발생한다면, 처리는 블록의 예외 섹션에서 해당 예외 처리기로 제어가 넘어 간다. PL/SQL 블록이 성공적으로 예외를 처리 한다면 이때 예외는 둘러싸는 블록이나 환경으로 전달 되지 않는다.

  -- 예외 전달
      예외를 처리하는 다른 방법은 실행 환경으로 예외를 전달하도록 하는 것이다. 예외가 블록의 실행부에서 발생하여 해당 예외 처리기가 없다면, PL/SQL 블록의 나머지 부분은 수행되지 못하고 종료된다. 

  - 예외 검출
      예외가 블록의 실행부에서 발생하면 블록의 예외부에 있는 해당 예외 처리부로 제어가 넘어간다.

  - 예외의 유형
     실행 중에 ERROR가 발생하면 프로그램이 중단되지 않고 예외에 대한 프로그램을 할 수 있다.

    정의된 ORACLE SERVER ERROR : PL/SQL코드에서 자주 발생하는 ERROR을 미리 정의함(선언할 수 없고 ORACLE SERVER이 암시적으로 발생)
    정의되지 않은 ORACLE SERVER ERROR : 기타 표준 ORACLE SERVER ERROR(사용자가 선언하고 ORACLE SERVER이 그것을 암시적으로 발생)
   사용자 정의 ERROR : 프로그래머가 정한 조건이 만족되지 않을 경우 발생(사용자가 선언하고 명시적으로 발생한다.)

  - 예외정의
EXCEPTION
	WHEN  exception1 [OR exception2, . . . .] THEN
		statement1;
		statement2;
		. . . . . .
	[WHEN  exception2 [OR exception3, . . . .] THEN
		statement3;
		statement4;
		. . . . . .]
	[WHEN  OTHERS THEN
		statement5;
		statement6;
		. . . . . .]
END;

  OTHERS : 명시적으로 선언되지 않은 모든 예외를 트랩하는 예외 처리 절

//*****************************************
#정의돤 오라클 서버 에러
   NO_DATA_FOUND(ORA-01403) : 데이터를 RETURN하지 않는 SELECT문장
   TOO_MANY_ROWS(ORA-01422) : 단일 행 SELECT는 하나 이상의 행을 RETURN
	
		declare
		type mytype is record(
			num insa.num%type,
			name insa.name%type,
			pay insa.basicpay%type
		);
		rec MYTYPE;
		begin
		--    select num, name, basicpay into rec from insa where num=9001;
		select num, name, basicpay into rec from insa where num=1001;
			DBMS_OUTPUT.PUT_LINE(rec.num||':'||rec.name||':'||rec.pay);
			
			exception
				when TOO_MANY_ROWS THEN DBMS_OUTPUT.PUT_LINE('데이터가 여러개 입니다.');
				when NO_DATA_FOUND THEN DBMS_OUTPUT.PUT_LINE('데이터가 없습니다.');
				when others then DBMS_OUTPUT.PUT_LINE('기타에러.');
				
		end;


//*****************************************
#사용자 정의 예외
  -- 선언 절차
    1. 선언 섹션에서 사용자가 선언한다.
        exception_name EXCEPTION;

        exception_name : 예외 이름을 정의

    2. 실행 섹션에서 명시적으로 예외를 발생하기 위해 RAISE문장을 사용한다.
        RAISE exception_name;
        exception_name : 앞에서 선언된 예외 이름을 기술한다.
		
		declare
			type mytype is record(
				num insa.num%type,
				name insa.name%type,
				pay insa.basicpay%type
			);
			rec MYTYPE;
			INSA_PAY_CHECK EXCEPTION; --사용자 정의 에러
			
		begin
		--    select num, name, basicpay into rec from insa where num=9001;
		select num, name, basicpay into rec from insa where num=1001;
			IF rec.pay >= 2500000 then raise INSA_PAY_CHECK; end if;
			DBMS_OUTPUT.PUT_LINE(rec.num||':'||rec.name||':'||rec.pay);
			
			exception
				when TOO_MANY_ROWS THEN DBMS_OUTPUT.PUT_LINE('데이터가 여러개 입니다.');
				when NO_DATA_FOUND THEN DBMS_OUTPUT.PUT_LINE('데이터가 없습니다.');
				when INSA_PAY_CHECK then DBMS_OUTPUT.PUT_LINE('급여가 250만원 이상입니다.');
				when others then DBMS_OUTPUT.PUT_LINE('기타에러.');
		end;
		/


//*****************************************
-- 예외 트래핑 함수
   에러가 발생 했을 때 두 함수를 사용하여 관련된 에러 코드 또는 메시지를 확인할 수 있다. 코드 또는 메시지에 따라 에러에 대해 취할 작업을 정할 수 있다.

  - SQLCODE : 에러코드
    0 : 에러 없이 정상적으로 실행되었음을 의미 
    1 : 사용자가 정의한 에러가 발생했음을 의미 
    +100 : 조건을 만족하는 행이 없음을 의미 
    양수값 : 다른 오라클 에러가 발생했음을 의미 
  - SQLERRM : 에러 메시지


//*****************************************
#RAISE_APPLICATION_ERROR
	1. 형식
		RAISE_APPLICATION_ERROR(error_number, error_message);
		RAISE_APPLICATION_ERROR(error_number, error_message, keep_errors);
	  - 표준화되지 않은 에러 코드와 에러 메시지를 리턴하기 위해 RAISE_APPLICATION_ERROR() 프로시져 사용
	  - RAISE_APPLICATION_ERROR를 만나면 처리를 중단하며, 자바등의 어플리케이션에는 에러를 던져주어 어플리케이션에서는 SQLException을 이용해서 에러를 확인할 수가 있다.
	  - error_message : 출력할 메시지
	  - error_number : -20999~-20000사이의 수 사용
	  - keep_errors : TRUE로 설정하면, stack 처럼 에러 리스트를 보존 가능하다. FALSE로 하면, 덮어쓰기를 한다. default로는 FALSE.


//*****************************************
#데이터 링크
	데이터베이스 링크를 이용하여 원격 데이터베이스를 액세스할 수 있다.

---------------------------------------------
#데이터 링크 권한 설정
	-- SYS 계정
	GRANT CREATE DATABASE LINK TO 사용자명;

---------------------------------------------
#데이터 링크 설정
	-- 사용자 계정
		CREATE DATABASE LINK 데이터링크명
			CONNECT TO 사용자명 IDENTIFIED BY "암호"
			USING 
			   '(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)
				   (HOST=아이피주소)(PORT=1521))
				(CONNECT_DATA=(SERVICE_NAME=ORCL)))';
						  -- ORCL : 데이터베이스 SID
						  -- 암호가 특수문자가 있는 경우에는 "" 안에 암호를 입력한다.

	-- 설정된 데이터 링크 테이블 SELECT
		SELECT 컬럼명, 컬럼명 FROM 테이블명@데이터링크명;

---------------------------------------------
#데이터 링크 삭제
	DROP DATABASE LINK 데이터링크명;

---------------------------------------------
-- 데이터 링크 설정 예

#데이터 링크 권한 설정
-- SYS 계정
GRANT CREATE DATABASE LINK TO sky;

#데이터 링크 설정
	-- 사용자 계정
		CREATE DATABASE LINK link_test
			CONNECT TO 사용자명 IDENTIFIED BY "암호"
			USING 
			   '(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)
				   (HOST=아이피주소)(PORT=1521))
				(CONNECT_DATA=(SERVICE_NAME=ORCL)))';
						  -- ORCL : 데이터베이스 SID
						  -- 암호가 특수문자가 있는 경우에는 "" 안에 암호를 입력한다.

		SELECT name, basicPay FROM insa@link_test;
								   -- 테이블명@데이터링크명

		SELECT num, name, buseo, city, basicpay FROM insa@link_test WHERE buseo='개발부'
					 UNION ALL
			   SELECT num, name, buseo, city, basicpay FROM INSA WHERE city='인천';

		DROP DATABASE LINK link_test;

		CREATE DATABASE LINK link_test
		CONNECT TO sky IDENTIFIED BY "java$!"	USING '(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP) (HOST=211.238.142.196)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=XE)))';
		select name, basicpay from insa@link_test; --테이블명@데이터링크명
		
		select num, name, buseo, city basicpay from insa@link_test where buseo='개발부' union all
		select num, name, buseo, city basicpay from insa where city='인천';

		select * from score1;

		insert into score1@link_test values('2013150027','임상근',100,100,100);

		select * from score3@link_test;
//*****************************************
#tnsnames.ora 에 정의된 connect_identifier 으로 접속
	CMD>sqlplus 사용자명/"암호"@orcl01
		-- orcl01 : tnsnames.ora 에 정의된 connect_identifier
		
		
//**********************************************
#port 번호란 ?

//*****************************************
#논리적 백업 : 스키마 및 자료 백업(테이블, 뷰, 프로시저...., 테이블 자료등)
	오라클은 startup 인 상태


#사용자별 백업
	cmd>exp userid=사용자명/암호 file=파일명.확장자 owner=사용자명
	cmd>dir

#사용자별 백업된 파일 복원
	cmd>imp userid=사용자명/암호 file=파일명.확장자 fromuser=사용자명 touser=사용자명

#논리적 백업 예
cmd>exp userid=sky/"java$!" file=back.dmp owner=sky

sky 사용자
DROP TABLE INSA PURGE;
SELECT * FROM TAB;

#복원
cmd>imp userid=sky/"java$!" file=back.dmp fromuser=sky touser=sky


//*****************************************
#테이블 스페이스
	-- 시스템 테이블 스페이스 종류
	   -- SYSTEM : 오라클 서버가 사용하는 공간으로 자료사전 테이블, 뷰가 저장
	   -- UNDOTBS1 : 트랜잭션의 ROLLBACK문을 실행할 때 변경이전의 데이터로 복구하기 위한 데이터 저장 공간
	   -- SYSAUX : 기본적으로 만들어지는 시스템 관리용 테이블스페이스
	   -- TEMP : GROUP BY, ORDER BY, HAVING, START WITH와 같은 SQL 문에서 SORT 작업이 발생되는데, 이 때 사용되는 데이터베이스 내부의 임시 저장 공간
	   -- USERS : 사용자가 테스트 할 수 있는 기본적으로 만들어지는 공간

	-- 참고
	   -- 1 block : 8KB
	   -- 8 block = 1 extent = 64 kb
	   -- 테이블 생성시 1 extent 생성
	   -- extent(table)이 모여서 segment
	   -- segment가 저장되는 공간 : 테이블 스페이스
	   -- 여러개의 테이블스페이스가 모여 데이터베이스


//*****************************************
-- SYS 계정
#dba_tablespaces 데이터 사전(현재 oracle db의 만들어진 테이블 스페이스 확인)
	SELECT * FROM dba_tablespaces;

	SELECT tablespace_name, initial_extent, min_extlen
				FROM dba_tablespaces;

	-- dba_data_files 데이터 사전
	SELECT file_name, tablespace_name FROM dba_data_files;

#테이블 스페이스 공간 확인
	SELECT tablespace_name, SUM(bytes), MAX(bytes) FROM DBA_FREE_SPACE
		   GROUP BY tablespace_name;

#테이블스페이스별 전체용량, 사용량, 가용량 확인
	SELECT u.tablespace_name "테이블스페이스명",
		   u.bytes / 1024000 "크기(MB)",
		  (u.bytes - SUM(NVL(f.bytes,0))) / 1024000 "사용됨(MB)",
		  (SUM(NVL(f.bytes,0))) / 1024000 "남음(MB)",
		  TRUNC((SUM(NVL(f.bytes,0)) / u.bytes) * 100,2) "남은 %"
	FROM dba_free_space f, dba_data_files u
	WHERE f.file_id(+) = u.file_id
	GROUP BY u.tablespace_name, u.file_name, u.bytes
	ORDER BY u.tablespace_name;

---------------------------------------------
#사용자 계정(사용중인 테이블 스페이스 확인)
	SELECT DISTINCT tablespace_name FROM user_tables;
	  -- TABS 는 USER_TABLES 의 동의어


//*****************************************
-- SYS 계정
#테이블 스페이스 생성(크기 : 100M)
	CREATE TABLESPACE sp_exam(논리적 별칭) DATAFILE 'd:/tbl/exam_data.dbf'(물리적 실제 위치)
			 SIZE 100M; 
		-- 용량은 짝수로 지정할것을 권장(디스크상에서 짝수로 용량이 늘어나므로 디스크용량낭비 방지)
		-- 데이터는 없어도 공간은 차지한다.
	SELECT * FROM v$tablespace;
	SELECT tablespace_name FROM dba_tablespaces;

---------------------------------------------
-- 사용자 계정
#sp_exam 테이블 스페이스에 테이블 작성
	CREATE TABLE sp1 (id NUMBER) TABLESPACE sp_exam;

	-- 확인
	SELECT DISTINCT tablespace_name FROM tabs;

	-- 자신에게 부여된 테이블스페이스의 용량 확인
	SELECT * FROM user_ts_quotas;

	  -- MAX_BYTES : -1 -> 무한대


//*****************************************
-- SYS 계정
# 테이블 스페이스 변경
	-- DEFAULT 테이블스페이스를 USERS로 변경
	ALTER USER 사용자 DEFAULT TABLESPACE USERS;

	-- TEMPORARY 테이블스페이스(정렬등에서 사용)를 TEMP로 변경
	ALTER USER 사용자 TEMPORARY TABLESPACE TEMP;


//*****************************************
-- SYS 계정
#테이블 스페이스를 제한 없이 사용
	GRANT UNLIMITED TABLESPACE TO 사용자;

	  * UNLIMITED TABLESPACE
		- instance별 여러 TABLESPACE 들을 제한 없이 사용할 수 있는 권한으로 기존에 옵션으로 설정되어 있던 quota 는 무시된다.


//*****************************************
-- SYS 계정
#테이블 스페이스 용량 변경
	ALTER DATABASE DATAFILE 'd:/tbl/exam_data.dbf'
				RESIZE 300M;


//*****************************************
-- SYS 계정
#테이블 스페이스 삭제
	DROP TABLESPACE sp_exam INCLUDING CONTENTS;
	  -- INCLUDING CONTENTS : 
			 -- 테이블스페이스에 테이블등 객체가 존재해도 삭제

	SELECT tablespace_name, contents FROM dba_tablespaces;

	-- 실제 파일을 탐색기로 제거해야 함


//*****************************************
-- SYS 계정
#테이블 스페이스 자동 확장
	CREATE TABLESPACE sp_exam
			 DATAFILE 'd:/tbl/db_data.dbf' SIZE 20M
			 AUTOEXTEND ON NEXT 10M MAXSIZE 1000M
			 EXTENT MANAGEMENT LOCAL UNIFORM SIZE 1M;
		 -- AUTOEXTEND ON : 자동확장

	SELECT * FROM v$tablespace;

	   -- AUTOEXTEND ON NEXT 10M MAXSIZE 1000M
		   -- 20M 을 다 사용하면 10M씩 자동 증가(1000M 까지)
	   -- EXTENT MANAGEMENT LOCAL UNIFORM SIZE 1M
		   -- 테이블스페이스의 각 extent size를 1M으로 지정
		   -- EXTENT : 일정한 수의 ORACLE 블럭(기본 1M)

#삭제
	DROP TABLESPACE sp_exam INCLUDING CONTENTS;


//*****************************************
#SYS 계정
	-- 테이블 스페이스 자동 확장(용량을 지정하지 않으면 무한대)
	--    (초기크기 : 20M)
	CREATE TABLESPACE sp_exam
		   DATAFILE 'd:/tbl/db_data.dbf'
		   SIZE 20M AUTOEXTEND ON;
    
#삭제
	DROP TABLESPACE sp_exam INCLUDING CONTENTS;
	
	
	
//*****************************************
#인덱스 => 최종 목표 검색 속도의 향상
#인덱스 개요
   index란 ROWID와 특정 컬럼의 정렬된 값을 set으로 하여 목록화한 것이다. 정렬해 놓은 컬럼의 각각의 값과 그에 해당하는 ROWID를 이용하여 곧 바로 data block에 접근하여 빠르게 해당 데이터를 찾아낸다. index는 자동으로 oracle에 의해 생성되기도 하고, 사용자가 직접 생성할 수 도 있다.
   index는 데이터를 보다 빠르게 검색할 수 있도록 도와주는 객체이다.
   DB의 성능을 향상시키고, 행(row)의 유일성을 보장하기 위해 사용된다.
   잘못된 인덱스는 DB의 성능을 오히려 저하시킨다.

#인덱스 특징
  빠른 검색을 위해 B*Tree를 사용하여 디스크의 입출력 횟수를 감소
  Oracle server가 자동으로 인덱스를 사용하고 유지 보수
  테이블과는 논리적, 물리적으로 독립
  생성, 삭제 시 다른 테이블이나 인덱스에 영향을 주지 않음
  각 인덱스는 page로 구성된 pointer와 컬럼 값으로 구성

#인덱스 생성
  자동 생성 : Unique 인덱스로 primary key 또는 Unique key 제약조건 생성시 자동으로 인덱스가 생성된다.(중복 값이 존재하면 생성 불가)
  수동 생성 : Non-Unique 인덱스로 access 시간을 향상시키기 위하여 사용자가 직접 생성한다. (중복 값이 존재 하는 경우)

#인덱스 종류
	 1) LOGICAL 인덱스
	   Single 컬럼 인덱스 : 하나의 컬럼 만 인덱스에 존재 
	   concatenated 인덱스(composite 인덱스) : 여러 컬럼을 결합하여 하나의 인덱스를 생성(32개의 컬럼 까지)
	   Unique 인덱스 : unique한 컬럼으로 인덱스
	   non-unique 인덱스 : unique하지 않은 여러 컬럼을 수동으로 인덱스
	   Function based(함수기반) 인덱스 : 연산자 또는 함수의 적용 결과에 따라 생성됨 

	 2) PHYSICAL 인덱스
	   partitioned(분할) 인덱스 : 분할된 테이블을 생성하는 경우에 적용됨 
	   non-partitioned(비 분할) 인덱스 : 인덱스를 분할하지 않음 
	   non-partitioned(비 분할) B-Tree 인덱스 : balance tree 구조로 저장 
	   non-partitioned(비 분할) Bitmap 인덱스 : bitmap 구조로 대용량인 경우 
	   normal 인덱스
	   reverse 인덱스 : B-Tree 인덱스에서 key 컬럼의 값을 뒤집어서 배열 

	   PARTITIONED INDEX는 테이블의 데이터 량이 너무 커서 분할된 테이블을 생성하는 경우에 적용되는 방법이다. 따라서 테이블 분할에 사용되었던 partition 기능을 사용하여 인덱스를 분할한다. 이렇게 분할된 인덱스는 관리하기도 편하고, 각 segment를 여러 개의 tablespace에 나누어 저장하므로 I/O의 경합을 줄이는 효과도 얻을 수 있다.

#index 생성 시 유의사항 (수십만건 이상일 경우 주로 사용)
	 1) 컬럼이 where절 또는 join 조건에서 자주 사용될 때
	 2) 컬럼에 광범위한 값이 포함될 때
	 3) 컬럼에 많은 수의 NULL값을 포함할 때
	 4) 대형 테이블이고 대부분의 질의가 10~15% 이하로 읽어 들일 것으로 예상할 때

#index를 생성하지 않는 경우 유의사항
	 1) 테이블에 자료의 양이 적을 때
	 2) 컬럼이 질의 조건으로 자주 사용되지 않을 때
	 3) 대부분의 질의들이 10~15%이상 읽어 들일 것으로 예상될 때
	 4) 테이블이 자주 갱신될 때


//*****************************************
#인덱스 형식
	-- B*Tree 인덱스(가장 많이 사용)
	CREATE INDEX 인덱스명 ON 테이블명(컬럼명, ...);

	-- Bitmap 인덱스
	CREATE BITMAP INDEX 인덱스명 ON 테이블명(컬럼명, ...);

	-- 함수기반 인덱스
	CREATE INDEX 인덱스명 ON 테이블명(함수식(컬럼명)| 산술식);

	-- 역방향 인덱스
	CREATE INDEX 인덱스명 ON 테이블명(컬럼명1,컬럼명2,...) REVERSE;

	-- 내림차순 인덱스
	CREATE INDEX 인덱스명 ON 테이블명(컬럼명1,컬럼명2,... DESC);

	  -- unique 옵션은 사용자가 직접 unique한 인덱스를 생성하고 할 때 사용한다. 디폴트는 non-unique 인덱스로 생성한다.
	  -- bitmap 옵션은 사용자가 직접 bitmap index를 생성하고 할 때 사용한다.


//*****************************************
#인덱스 정보 조회
SELECT index_name, index_type, table_owner, table_name FROM user_indexes;
SELECT index_name, table_name, column_name FROM user_ind_columns;


//*****************************************
#인덱스 삭제
DROP INDEX 인덱스명;


//*****************************************
#인덱스 생성
   자신 소유의 스키마에서 이 문을 실행하려면 UNLIMITED TABLESPACE 시스템 권한이 있어야 하고, 다른 스키마인 경우 CREATE ANY INDEX 시스템 권한이 있어야 한다
    RESOURCE 롤에는 기본적으로 인덱스를 만들 수 있는 권한이 있다.


//*****************************************
#B*Tree 인덱스
  어떤 행에 대한 access 회수도 동일하게 하는 바이너리의 균형 탐색 tree이다. 찾으려는 행이 테이블 시작이나 끝 또는 중간에 있어도 거의 같은 횟수 내에 지정된 값을 access하는 효율적인 방법이다.

  B-tree index는 binary search index의 약자이며, 일명 balence tree index라고도 부른다.

  밸런스 트리 인덱스를 만드는 기준은 다음과 같다.
   -- 인덱스를 만들어야 하는 열은 DML(insert, update, delete, select) 문의 WHERE 절에 자주 나오는 열이다.
   -- 조인되는 테이블의 조인 열에는 인덱스를 만들어야 한다.
  -- 인덱스에 NULL 값이 저장되지 않기 때문에, NULL 값이 많은 열에 인덱스를 만들면 유리하다.
  -- DML 문장의 WHERE 절에 의해 검색되는 데이터의 테이블 전체의 10～15%의 범위에 속할 때 인덱스를 만들어야 한다.
  -- 하나의 테이블에 인덱스를 많이 만들면 update, delete할 때 실행속도가 늦다.(하나의 테이블에 3~4개의 인덱스가 적당하다)


//------------------------------------------
#인덱스 테스트
	시퀀스 만들기
	CREATE SEQUENCE demo_seq;

	테이블 만들기
	CREATE TABLE demo (
	   num NUMBER PRIMARY KEY
	   ,subject VARCHAR2(500) NOT NULL
	   ,content VARCHAR2(4000) NOT NULL
	   ,created DATE DEFAULT SYSDATE
	);

	데이터 추가를 위한 프로시져 작성
	CREATE OR REPLACE PROCEDURE pdemoInsert
	IS
	  n NUMBER := 0;
	BEGIN
		WHILE n<1000000 LOOP -- 1백만개
			n:=n+1;
			INSERT INTO demo(num, subject, content) VALUES
			   (demo_seq.NEXTVAL, '제목'||n, '내용입니다.'||n);
		END LOOP;
		COMMIT;
	END;
	/

	EXEC pdemoInsert;
#페이징 처리 테스트
	--row_number() 이용하여 페이징
		select * from(
			select tb.*,row_number() over(order by num desc) as rnum
				from demo tb
		)where rnum >= 900001 and rnum <= 900010 order by num DESC;
		-- =>1.945 sec

	-- rownum 이용한 페이징
		select * from(
			select tb.*, rownum rnum from
			(
			   select * from demo order by num desc
			)tb where rownum <=900010
		) where rnum >=900001;
		-- => 0.261 초
	
	-- between 이용한 페이징 = 관계연산자보다 속도가 느리며, 아래 두가지 예보다 위가 더 빠르다.
		select * from(
			select tb.*, rownum rnum from
			(
			   select * from demo order by num desc
			)tb
		) where rnum between 900001 and 900010;
		-- => 0.732 sec

	-- 단순 연산을 이용한 패키지
		select * from(
			select tb.*, rownum rnum from
			(
			   select * from demo order by num desc
			)tb
		) where rnum >=900001 and rnum <=900010;
		-- => 0.255 sec
		
#성능 측정을 위해 시간 표시
SET TIMING ON

#인덱스를 작성하지 않은 상태
	SELECT subject, content FROM demo 
		   WHERE subject = '제목500000';

	-- B*Tree 인덱스 작성
	CREATE INDEX demo_subject_idx ON demo(subject);

	-- 인덱스 확인
	SELECT * FROM user_indexes;

	-- 인덱스를 작성한 상태
	SELECT subject, content FROM demo 
		   WHERE subject = '제목500000';
			select subject, content from demo where subject = '제목500000';
			--=>0.07 sec
			create index demo_subject_idx on demo(subject);

			select subject, content from demo where subject = '제목500000';
			--=>0.037 sec
	-- 인덱스를 만들지 않은 content로 확인
	SELECT subject, content FROM demo 
		   WHERE content = '내용입니다.500000';

		   before
			SELECT subject, content FROM demo WHERE content = '내용입니다.500000';
				   =>0.109 sec
			create index deom_content_idx on demo(content);
			after
			SELECT subject, content FROM demo WHERE content = '내용입니다.500000';
				   =>0.021 sec

#인덱스 삭제
	DROP INDEX demo_subject_idx;
	SELECT * FROM user_indexes;

	DROP TABLE demo PURGE;
	DROP SEQUENCE demo_seq;

//------------------------------------------
-- 인덱스 실습 테이블 작성
CREATE TABLE insa1 AS SELECT * FROM INSA;

INSERT INTO insa1 SELECT * FROM INSA;
INSERT INTO insa1 SELECT * FROM INSA;
INSERT INTO insa1 SELECT * FROM INSA;
INSERT INTO insa1 SELECT * FROM INSA;
INSERT INTO insa1 SELECT * FROM INSA;
-- 여러번 실행(많이)

-- 인덱스 실습용 자료 추가
INSERT INTO insa1 (num, name, ssn, ibsaDate, city, tel, buseo, jikwi, basicPay, sudang) VALUES
  (9001, '자바다', '010101-1022432', SYSDATE, '서울', '011-2356-4528', '기획부', '사원', 1150000, 100000);

COMMIT;

-- 성능측정을 위해 시간을 표시
SET TIMING ON

-- 인텍스를 작성하지 않은 상태에서 검색
SELECT name, ssn FROM insa1 WHERE name='자바다';

SELECT index_name, table_owner, table_name FROM user_indexes;

-- 인텍스 작성
	CREATE INDEX insa_name_idx ON insa1(name);

	SELECT index_name, table_owner, table_name FROM user_indexes;

	SELECT name, ssn FROM insa1 WHERE name='자바다';

-- 인덱스 삭제
	DROP INDEX insa_name_idx;


//*****************************************
#Bitmap 인덱스(Express는 지원하지 않음)
   Bitmap 인덱스는 위에서 내려오는 것과는 달리 여러 단계를 거치지 않고 단번에 해당 block에 찾아가는 것을 의미한다. 즉, 소수의 다른 값을 가진 컬럼에 대해 적합한 타입의 인덱스인데, 컬럼에 대해 극소수의 다른 값이 존재하고 쿼리에서 제한조건(where절의 AND, OR연산)으로서 이 컬럼을 자주 사용하는 경우 컬럼에 대해 bitmap 인덱스 사용을 고려해야 한다.

    -- 아주 큰 테이블에서 검색할 때 사용
    -- UPDATE, INSERT, DELETE 문이 자주 실행되지 않는 테이블에 사용
    -- 일반 인덱스의 크기가 너무 크거나 인덱스 생성 시간이 너무 많이 소요되는 경우에 사용

	CREATE BITMAP INDEX  insa_name_idx ON insa1(name);

	SELECT index_name, table_owner, table_name FROM user_indexes;

	SELECT name, ssn FROM insa1 WHERE name='자바다';

#인덱스 삭제
	DROP INDEX insa_name_idx;


//*****************************************
#Function based index(함수 기반 인덱스)
	 -- 인덱스가 생성될 컬럼에는 함수나 산술식, 상수, 사용자 정의 함수를 적용할 수 있다.
	 -- 함수기반 인덱스를 생성하면 bitmap 인덱스가 생성된다(default).
	 -- 함수기반 인덱스 생성은 system 권한이 있어야 가능하다.

	CREATE INDEX insa_ssn_idx ON insa1(SUBSTR(ssn, 8, 1));

	SELECT index_name, table_owner, table_name FROM user_indexes;

	SELECT name, ssn FROM insa1 WHERE SUBSTR(ssn, 8, 1) IN (1, 3);

#인덱스 삭제
	DROP INDEX insa_ssn_idx;


//*****************************************
#인덱스 재생성
   인덱스가 만들어진 후 INSERT, UPDATE가 일어 난 경우 오라클이 자동으로 인덱스를 갱신하지만 DELETE는 논리적인 삭제 과정만 일어나고 인덱스는 그대로 남아 있다. 이런 경우 인덱스를 재 생성한다.
   ALTER INDEX 인덱스명 REBUILD;


   //*****************************************
-- regular expression(정규식)
-- 실습용 자료
CREATE TABLE test (
       num NUMBER(5) NOT NULL 
              CONSTRAINT pk_test_num PRIMARY KEY
       ,name VARCHAR2(20)
       ,email VARCHAR2(50)
    );

INSERT INTO test (num, name, email) VALUES
      (1, '한라산', 'hanlasan@abc.co.kr');
INSERT INTO test (num, name, email) VALUES
      (2, '백두산', 'backdusan@abc.co.kr');
INSERT INTO test (num, name, email) VALUES
      (3, '금강산', 'gumgangsan@abc.co.kr');
INSERT INTO test (num, name, email) VALUES
      (4, 'kim1', 'kim1@abc.co.kr');
INSERT INTO test (num, name, email) VALUES
      (5, 'kim22', 'kim22@abc.co.kr');
INSERT INTO test (num, name, email) VALUES
      (6, 'kim323', 'kim323@abc.co.kr');
INSERT INTO test (num, name, email) VALUES
      (7, 'kim333', 'kim333@abc.co.kr');
INSERT INTO test (num, name, email) VALUES
      (8, 'Hello1234', 'hello@abc.c.kr');
INSERT INTO test (num, name, email) VALUES
      (9, 'arirang', 'http://arirang.co.kr');
INSERT INTO test (num, name, email) VALUES
      (10, 'seoul', 'http://seoul.co.kr');
INSERT INTO test (num, name, email) VALUES
      (11, 'home', 'http://home.co.kr');
COMMIT;

---------------------------------------------
-- 패턴(Pattern)
    ^ : 패턴으로 시작하는 line 출력
    $ : 패턴으로 끝나는 line 출력
    . : 한문자
    * : 0자이상
    [ ] : 패턴에 해당하는 한문자
    [^] : 패턴에 해당하지 않은 한문자

---------------------------------------------
-- regexp_like : 패턴이 포함된 문자열을 반환
SELECT * FROM test WHERE regexp_like(name, '^[한백]');
          -- name 필드에서 "한" 또는 "백"자로 시작하는 레코드

SELECT * FROM test WHERE regexp_like(name, '강산$');
          -- name 필드에서 "강산"자로 끝나는 레코드

SELECT * FROM test WHERE regexp_like(name, 'kim*');
          -- name 필드에서 "kim"으로 시작하는 레코드

SELECT * FROM test WHERE regexp_like(name, 'kim3?3');
          -- name 필드에서 "kim3" 다음의 한 글자는 어느 것이 와도 상관없으며 다음글자는  "3" 인 레코드

SELECT * FROM test WHERE regexp_like(name, 'kim[0-3]{2}');
          -- name 필드에서 "kim"으로 시작하고 0-3 사이의 문자가 2번 이상 반복 하는 레코드

SELECT * FROM test WHERE regexp_like(name, 'kim[2-3]{2,3}');
          -- name 필드에서 "kim"으로 시작하고 2-3 사이의 문자가 2~3번 반복 하는 레코드

SELECT * FROM test WHERE regexp_like(name, 'kim[^1]');
SELECT * FROM test WHERE regexp_like(name, '[^1]$');
          -- kim 다음에 1이 아닌 레코드([^1] 는 부정)

---------------------------------------------
-- regexp_instr : 문자열 중에서 임의의 패턴이 위치한 자리를 반환
SELECT name, regexp_instr(name,'[^[:alpha:]]') position
        FROM test;
          -- 영문자가 아닌 문자의 위치

-- 숫자인지 판단
WITH temp_table AS (
   SELECT '컴퓨터' product, '650' price FROM DUAL
   UNION ALL
   SELECT '노트북 product, '700' price FROM DUAL
   UNION ALL
   SELECT '테블릿' product, '￦460' price FROM DUAL
)
SELECT product
           , TO_NUMBER(price) price
FROM temp_table
WHERE REGEXP_INSTR(price,'[^0-9]') = 0;

---------------------------------------------
-- regexp_substr : 문자열 내에 존재하는 임의의 패턴을 반환
SELECT email, regexp_substr(email, '[^@]+') id FROM test;

---------------------------------------------
-- regexp_replace : 문자열을 지정한 패턴으로 치환
SELECT regexp_replace ('Kim gil dong','(.*) (.*) (.*)', '\3 \2 \1')
           FROM DUAL;
     -- dong gil Kim

SELECT UNIQUE regexp_replace(email, 'http://([^/]+).*', '\1')
       FROM test;

SELECT email, regexp_replace(email, 'arirang', 'seoul') FROM test;


---------------------------------------------
-- regexp_count : 특정문자열의 개수를 세는 함수(11g)
-- 처음부터 a의 개수 count
SELECT name, regexp_count(name, 'a') FROM test;

-- 세번째부터 a의 개수 count
SELECT name, regexp_count(name, 'a', 3) FROM test;

SELECT name, regexp_count(name, '[0-9]') FROM test;





//*****************************************
// 암호화 및 복호화
-- 암호화 기능을 이용하려면 DBMS_OBFUSCATION_TOOLKIT 패키지를 이용한다.
  
-- 이 패키지는 4개의 프로시져로 이루어져 있다.
- VARCHAR2 타입을 Encrypt/Decrypt할 수 있는 2개의 프로시져
- RAW 타입을 Encrypt/Decrypt할 수 있는 2개의 프로시져
  -- (다른 타입은 지원하지 않으므로 number인 경우는 to_char 이용)


//*****************************************
-- 암호화 및 복호화 패키지 추가
-- SYS 계정
-- 1) Oracle 11g Enterprise Edition 인 경우
       (C:/app/user/product/11.2.0/dbhome_1은 오라클이 설치된 경로)
  SQL>@C:/app/user/product/11.2.0/dbhome_1/rdbms/admin/dbmsobtk.sql  SQL>@C:/app/user/product/11.2.0/dbhome_1/rdbms/admin/prvtobtk.plb

-- 2) Oracle 11g Express Edition인 경우
  SQL>@C:/oraclexe/app/oracle/product/11.2.0/server/rdbms/admin/dbmsobtk.sql
  SQL>@C:/oraclexe/app/oracle/product/11.2.0/server/rdbms/admin/prvtobtk.plb

-- 3) ORACLE_HOME 환경변수가 설정된 경우
  SQL>@$ORACLE_HOME/rdbms/admin/dbmsobtk.sql
  SQL>@$ORACLE_HOME/rdbms/admin/prvtobtk.plb
  
--------------------------------------------
-- 모든 사용자가 DBMS_OBFUSCATION_TOOLKIT 패키지의 프로시져를 사용 할 수 있는 권한 부여
-- SYS 계정
SQL>GRANT execute ON dbms_obfuscation_toolkit TO public;


//*****************************************
-- 사용자 계정
-- 패키지 선언
//문자밖에 못함, 숫자쓰고 싶으면 to_char사용해야한다.
CREATE OR REPLACE PACKAGE CryptIT AS 
	--암호화 함수
   FUNCTION encrypt( str VARCHAR2,  
                     hash VARCHAR2 ) RETURN VARCHAR2;
    --복호화 함수
   FUNCTION decrypt( xCrypt VARCHAR2, 
                     hash VARCHAR2 ) RETURN VARCHAR2;
END CryptIT;
/

-- 패키지 몸체
CREATE OR REPLACE PACKAGE BODY CryptIT IS 
   crypted_string VARCHAR2(2000);
 
   FUNCTION encrypt(str VARCHAR2, hash VARCHAR2)
   RETURN VARCHAR2
   IS
       pieces_of_eight NUMBER := ((FLOOR(LENGTH(str)/8 + .9)) * 8);
    BEGIN
       dbms_obfuscation_toolkit.DESEncrypt(
               input_string     => RPAD(str, pieces_of_eight ),
               key_string       => RPAD(hash,8,'#'), 
               encrypted_string => crypted_string );

      RETURN crypted_string;
   END;
 
   FUNCTION decrypt( xCrypt VARCHAR2, hash VARCHAR2 )
   RETURN VARCHAR2 IS
   BEGIN
      dbms_obfuscation_toolkit.DESDecrypt(
               input_string     => xCrypt, 
               key_string       => RPAD(hash,8,'#'), 
               decrypted_string => crypted_string );

      RETURN TRIM(crypted_string);
   END;
END CryptIT;
/

-- DESEncrypt, DESDecrypt 함수는 인수가 VARCHAR2, RAW인 프로시져가 있다.
-- raw : 2진데이터, UTL_RAW.CAST_TO_RAW : 문자열을 2진 데이터로
-- input_string   VARCHAR2(32) := 'abcd';
-- raw_input      RAW(128) := UTL_RAW.CAST_TO_RAW(input_string);
-- str VARCHAR2(32) := UTL_RAW.CAST_TO_VARCHAR2(raw_input));

---------------------------------------------
-- 테스트
CREATE TABLE exam(id NUMBER, pass VARCHAR2(100));

-- 입력
-- CryptIT.encrypt(비밀번호, 키값)
INSERT INTO exam VALUES(1, CryptIT.encrypt('12345', 'test'));
COMMIT;
SELECT * FROM exam;

-- 데이터 조회
 -- Decrypt 하지 않으면 결과값이 출력되지 않는다.
SELECT id, CryptIT.decrypt(pass,'test') FROM exam1 WHERE pass = CryptIT.encrypt('12345', 'test');

SELECT id, pass FROM exam;
SELECT id, DUMP(pass) FROM exam; --  DUMP : oracle 내부 저장형식

SELECT id, CryptIT.decrypt(pass,'test') pass FROM exam;

