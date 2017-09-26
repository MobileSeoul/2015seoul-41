package com.cdk.helpme.api;

public class ApiDomains {
    // TODO : 좋은 이름으로 바꿀 것
    // TODO : 지금도 충분히 멋있어요 윤구대리님

    public static final String 응급실실시간가용병상정보조회 = "/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // STAGE1 주소(시도) VARCHAR2(16) 1 서울특별시
    // STAGE2 주소(시군구) VARCHAR2(60) 0 강남구

    public static final String 응급의료기관위치정보조회 = "/ErmctInfoInqireService/getEgytLcinfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // WGS84_LON 병원경도 VARCHAR2(30) 1 127.08515659273706
    // WGS84_LAT 병원위도 VARCHAR2(30) 1 37.488132562487905

    public static final String 응급의료기관목록정보조회 = "/ErmctInfoInqireService/getEgytListInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // Q0 주소(시도) VARCHAR2(16) 1 서울특별시
    // Q1 주소(시군구) VARCHAR2(60) 1 -
    // QT 진료요일 VARCHAR2 1 　 월~일요일(1~7), 공휴일(8)
    // QZ 기관분류 CHAR(1) 1 　 CODE_MST의 'H000' 참조(A~H, J~O, Q)
    // QD 진료과목 VARCHAR2 1 　 CODE_MST의 'D000' 참조(D000~D029)
    // ORD 정렬기준 VARCHAR2 1 NAME 기관명(NAME)/주소(ADDR)

    public static final String 응급의료기관기본정보조회 = "/ErmctInfoInqireService/getEgytBassInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // HPID 기관ID VARCHAR2(10) 1 A0000028

    public static final String 중중질환자수용가능정보조회 = "/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // STAGE1 주소(시도) VARCHAR2(16) 1 서울특별시
    // STAGE2 주소(시군구) VARCHAR2(60) 0 -
    // SM_TYPE 질환/수술(시술)명 　 0 - Y000:Emergency gate keeper
    // Y000:뇌출혈수술 // Y000:뇌경색의재관류 // Y000:심근경색의재관류 // Y000:복부손상의수술 //
    // Y000:사지접합의수술 // Y000:응급내시경 // Y000:응급투석 // Y000:조산산모 // Y000:정신질환자 //
    // Y000:신생아 // Y000:중증화상

    public static final String 병의원목록정보조회 = "/HsptlAsembySearchService/getHsptlMdcncListInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // Q0 주소(시도) VARCHAR2(16) 1 서울특별시
    // Q1 주소(시군구) VARCHAR2(60) 0 -
    // QZ 기관구분 CHAR(1) 0 - CODE_MST의 'H000' 참조(B:병원, C:의원)
    // QD 진료과목 VARCHAR2 0 - CODE_MST의 'D000' 참조(D001~D029)
    // QT 진료요일 VARCHAR2 0 - 월~일요일(1~7), 공휴일(8)
    // ORD 정렬기준 VARCHAR2 1 NAME 병원명(NAME)/주소(ADDR)

    public static final String 병위원위치정보조회 = "/HsptlAsembySearchService/getHsptlMdcncListInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // WGS84_LON 병원경도 VARCHAR2(30) 1 127.0851566
    // WGS84_LAT 병원위도 VARCHAR2(30) 1 37.48813256

    public static final String 병위원기본정보조회 = "/HsptlAsembySearchService/getHsptlBassInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // HPID 기관ID VARCHAR2(10) 1 A0000028

    public static final String 명절진료가능한기관상세정보조회 = "/HolidyEmgncClnicInsttInfoInqireService/getHolidyClnicPosblDetailInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // HPID 기관ID VARCHAR2(10) 1 A0000028

    public static final String 명절진료가능한기관위치정보조회 = "/HolidyEmgncClnicInsttInfoInqireService/getHolidyClnicPosblEgyptLcinfoInquire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // WGS84_LON 병원경도 VARCHAR2(30) 1 127.0851566
    // WGS84_LAT 병원위도 VARCHAR2(30) 1 37.48813256

    public static final String 약국목록정보조회 = "/ErmctInsttInfoInqireService/getParmacyListInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // Q0 주소(시도) VARCHAR2(16) 1 서울특별시 　
    // Q1 주소(시군구) VARCHAR2(60) 0 - 　
    // QT 진료요일 VARCHAR2 0 - 월~일요일, 공휴일 : 1~8
    // ORD 정렬기준 VARCHAR2 1 ADDR 병원명정렬기준(주소:ADDR/기관명:NAME)

    public static final String 약국위치정보조회 = "/ErmctInsttInfoInqireService/getParmacyLcinfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // WGS84_LON 병원경도 VARCHAR2(30) 1 127.0851566
    // WGS84_LAT 병원위도 VARCHAR2(30) 1 37.48813256

    public static final String 약국기본정보조회 = "/ErmctInsttInfoInqireService/getParmacyBassInfoInqire";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // HPID 기관ID VARCHAR2(10) 1 N0002117

    public static final String 코드마스터정보조회 = "/CodeMast/info";
    // 항목명(영문) 항목명(국문) 항목크기 항목구분 샘플데이터 항목설명
    // CM_MID 대분류코드 VARCHAR2(20) 1 S000
}
