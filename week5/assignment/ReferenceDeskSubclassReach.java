public class ReferenceDeskSubclassReach {
    static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) {
            return "ALLOWED";
        }
        if ("private".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }
        if ("protected".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext)
                    || "SAME_PACKAGE".equals(accessorContext)
                    || "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)
                    ? "ALLOWED" : "DENIED";
        }
        return "DIFFERENT_PACKAGE".equals(accessorContext)
                || "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)
                || "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE".equals(accessorContext)
                ? "DENIED" : "ALLOWED";
    }

    static String describeContext(String accessorContext) {
        String[] parts = accessorContext.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
    }
}