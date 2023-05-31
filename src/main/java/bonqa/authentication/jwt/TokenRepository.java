package bonqa.authentication.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(
            value =
                    """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Integer id);

    @Modifying
    @Query(value = """
      delete from Token t
      where t.expired = true or t.revoked = true
      """)
    void deleteExpiredOrRevokedTokens();

    Optional<Token> findByToken(String token);
}
