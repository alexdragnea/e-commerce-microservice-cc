package acs.upb.userservice.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {
  private final LoadingCache<String, Integer> loginAttemptCache;

  public LoginAttemptService() {
    super();
    loginAttemptCache =
        CacheBuilder.newBuilder()
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .maximumSize(100)
            .build(
                new CacheLoader<String, Integer>() {
                  public Integer load(String key) {
                    return 0;
                  }
                });
  }

  public void evictUserFromLoginAttemptCache(String email) {
    loginAttemptCache.invalidate(email);
  }
}
