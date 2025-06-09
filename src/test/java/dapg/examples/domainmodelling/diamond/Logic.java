package dapg.examples.domainmodelling.diamond;

import dapg.examples.domainmodelling.diamond.UserDomain.UserVerificationService;
import dapg.examples.domainmodelling.diamond.UserDomain.VerifiedUser;
import dapg.examples.domainmodelling.diamond.UserDomain.UnverifiedUser;

public class Logic {

    // java: dapg.examples.domainmodelling.diamond.Domain.Aha has private access in dapg.examples.domainmodelling.diamond.Domain
//    public static void doStuff(Aha aha) {
//        System.out.println("Value: " + aha);
//    }

    public static void doStuffWithUnverifiedUser(UnverifiedUser unverifiedUser) {
        System.out.println("Value: " + unverifiedUser);
    }

    public static void doStuffWithVerifiedUser(VerifiedUser verifiedUser) {
        System.out.println("Value: " + verifiedUser);
    }

    public static class UserVerificationServiceImpl extends UserVerificationService {
        @Override
        public VerifiedUser verifyUser(UnverifiedUser user) {
            System.out.println("Verifying user");
            return safeCast(user);
        }
    }
}
