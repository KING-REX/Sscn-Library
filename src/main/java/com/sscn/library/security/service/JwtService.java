package com.sscn.library.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "308204BE020100300D06092A864886F70D0101010500048204A8308204A402010002820101008317CFB51163862EDD0378E50C953EB4CECB4D96CF5E15B233AEA65211105110AD47310853008A487F031857E8858310AC8F2FFE737C1B917A27CCC0FD751EC7EBF34DB2F70118375ADDBFCC1A6C270798AF5C8B5295D54ECD8C797661843914789EFBE7C8675907C493D4466E2E580E35CBFBF335C0463018D8BD92FA9D271AAD36458A7A05C153ACD83032F5E63B5F55A2E5833EE9200D0B14E357DC3129326C5444A3FB2258298591B714A7957DC97B5ABE97EA3EED4404E9B0DCA4D1049C56632FCEEC13E223EECCE3B21CC2098BFC53EC40DF5719930E04523BD6384C02351C90C3E20E641B03AC5739BEEA88B398D7B5DED00EDBEA29CDE5246340DFCB0203010001028201005BED65AD1E8D80F9F9E8466DB05E3403CC83CA5BF7FC8D1052A7A560253120F7A5DB276A23F25C062C72FA2E231304EEBB63098FD061F65F149F36391B43771B50BD63FE1CB1FACE4E7CF3D367AFF1CAA4EA4BE6333A78C372E6900250ADCCED2B0CA9AF46DC1A159566E8D39ECE06D7A827EBFD07F35604C257B2D08FC7863466D6695DC9F6B1696AD2275935B927246A06B1C909A21687AAC5080A9FBBD806F7086D027529934C57C3F2E4E244ADE0D021265D6AB833920E99941475FA7AE167A5351F36CCC7611ADC06B56AB3E78361B4E77313FAC0E948DCBAACBF3E3EBB19C812DE859D581983D33044C7EB048128197BD819D354C26B0773E256577FD902818100F1EFE86FDC247B8FB0BC815728EA49EEA1AA9829D08D9D4E1593C8670D0B2A3BE3EAF86AE49EF81BE72EA7C2ADF4DB89DA19813377E2F849A40FF5ADC9ED908AB4007A813B3BCB30427F57B7ACC3FAF93A609ABBDFC07BC0E6497769D396FC433433609040835D756D1EFE08E7CC60EE2FD07A87EE89273B3F6BD2D5596068E5028181008AB6830C702A980BB2783219F1A8660DCE0441E031AC2CD5AAFEF9C10591A107AF116126D806236B69C46086DC1CBB1B4B4243139D22F9B78F64B87B2CD2FE5AF5E0942DEC5061C91BAD6090C3A44588F987D338ACF525AEF0B31905CB1C184EAC4FD78E63236AEBBB367749B77EE0BB049F93E0C5E84400239BD933B12E0AEF0281807BB0EE231267CC8C400D800AF1AE26B24C110DCE830383E30201163A295F05EEACADDAE7BB8ED2C66141671F8F145A9175E2083E2A12EE61A7F148E7DB0F0C10FDCA28D79D530AA4D7BA873C2EA5DBD043DA5401AD79994B9BF0FF8E29514E1385C2A6A1D979CFF4531FBF3D0A556151253CEF084419F6E6C19BFCA47B17614902818100833EE746088734AFB4A0A77F06B8F83ECD9417A4823B4F7CA7BB7322A01B478AD6A9920BC7D01F094DDCE21C17FE134762F1023877B095288A43998DEA3E852E1980F193760A244BBDA5513E07EED1602601CEE7CC3673FE4F7E9609764151FA766D611E374D56A84ECDDD1661C969E76A1B7736F0EAB014959CAB698E966AFF02818100B51EBA908EA76219E40EB956039BA8D31A96B7311593553ECBC6268940F179D5975560E35ADFA39C30CCDE551D814A1C34913E719ECF833A70446C9A06F02766914C222B42C4E95D9FC7E88A501278445551A327385AF18555FA79609347074625C22C75CDD7FD6D1F2CF472A3E6A5EB358DCD6F7E205EA909BC85099643A63A";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractDateIssued(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken (UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    )
    {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 24)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        Claims claims = extractAllClaims(token);
        boolean check1 = claims.getIssuedAt().before(new Date());
        boolean check2 = claims.getIssuedAt().before(claims.getExpiration());
        boolean check3 = username.equals(userDetails.getUsername());
        boolean check4 = !isTokenExpired(token);

        return check1 && check2 && check3 && check4;
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public Claims extractAllClaims(String token) throws UnsupportedJwtException, MalformedJwtException, SignatureException, ExpiredJwtException, IllegalArgumentException {
        System.out.println("Wrong token...");
        System.out.println(token);
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
