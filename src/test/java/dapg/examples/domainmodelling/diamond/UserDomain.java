package dapg.examples.domainmodelling.diamond;

public class UserDomain {

    public sealed interface User {
        String name();
        int age();
    }


    public sealed interface UnverifiedUser extends User {
        static UnverifiedUser of(String name, int age) {
            return new UserImpl(name, age);
        }
    }


    public sealed interface VerifiedUser extends User {}


    private record UserImpl(
            String name,
            int age
    ) implements UnverifiedUser, VerifiedUser {}


    public abstract static class UserVerificationService {
        protected enum UserVerificationPermission {INSTANCE}

        public abstract VerifiedUser verifyUser(UnverifiedUser user);

        protected VerifiedUser safeCast(UnverifiedUser unverifiedUser) {
            return switch (unverifiedUser) {
                case UserImpl verifiedUser -> verifiedUser;
            };
        }
    }
}
