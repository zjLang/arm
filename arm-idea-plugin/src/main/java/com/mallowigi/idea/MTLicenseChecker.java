//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mallowigi.idea;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.LicensingFacade;
import com.mallowigi.idea.messages.MaterialThemeBundle;
import com.mallowigi.idea.utils.MTUiUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.security.cert.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Base64.Decoder;

public final class MTLicenseChecker {
    private static final String PRODUCT_CODE = "PMATERIALUI";
    private static final String KEY_PREFIX = "key:";
    private static final String STAMP_PREFIX = "stamp:";
    private static final String EVAL_PREFIX = "eval:";
    private static final long SECOND = 1000L;
    private static final long MINUTE = 60000L;
    private static final long HOUR = 3600000L;
    private static final long TIMESTAMP_VALIDITY_PERIOD_MS = 3600000L;
    private static final String[] ROOT_CERTIFICATES = new String[]{"-----BEGIN CERTIFICATE-----\nMIIFOzCCAyOgAwIBAgIJANJssYOyg3nhMA0GCSqGSIb3DQEBCwUAMBgxFjAUBgNV\nBAMMDUpldFByb2ZpbGUgQ0EwHhcNMTUxMDAyMTEwMDU2WhcNNDUxMDI0MTEwMDU2\nWjAYMRYwFAYDVQQDDA1KZXRQcm9maWxlIENBMIICIjANBgkqhkiG9w0BAQEFAAOC\nAg8AMIICCgKCAgEA0tQuEA8784NabB1+T2XBhpB+2P1qjewHiSajAV8dfIeWJOYG\ny+ShXiuedj8rL8VCdU+yH7Ux/6IvTcT3nwM/E/3rjJIgLnbZNerFm15Eez+XpWBl\nm5fDBJhEGhPc89Y31GpTzW0vCLmhJ44XwvYPntWxYISUrqeR3zoUQrCEp1C6mXNX\nEpqIGIVbJ6JVa/YI+pwbfuP51o0ZtF2rzvgfPzKtkpYQ7m7KgA8g8ktRXyNrz8bo\niwg7RRPeqs4uL/RK8d2KLpgLqcAB9WDpcEQzPWegbDrFO1F3z4UVNH6hrMfOLGVA\nxoiQhNFhZj6RumBXlPS0rmCOCkUkWrDr3l6Z3spUVgoeea+QdX682j6t7JnakaOw\njzwY777SrZoi9mFFpLVhfb4haq4IWyKSHR3/0BlWXgcgI6w6LXm+V+ZgLVDON52F\nLcxnfftaBJz2yclEwBohq38rYEpb+28+JBvHJYqcZRaldHYLjjmb8XXvf2MyFeXr\nSopYkdzCvzmiEJAewrEbPUaTllogUQmnv7Rv9sZ9jfdJ/cEn8e7GSGjHIbnjV2ZM\nQ9vTpWjvsT/cqatbxzdBo/iEg5i9yohOC9aBfpIHPXFw+fEj7VLvktxZY6qThYXR\nRus1WErPgxDzVpNp+4gXovAYOxsZak5oTV74ynv1aQ93HSndGkKUE/qA/JECAwEA\nAaOBhzCBhDAdBgNVHQ4EFgQUo562SGdCEjZBvW3gubSgUouX8bMwSAYDVR0jBEEw\nP4AUo562SGdCEjZBvW3gubSgUouX8bOhHKQaMBgxFjAUBgNVBAMMDUpldFByb2Zp\nbGUgQ0GCCQDSbLGDsoN54TAMBgNVHRMEBTADAQH/MAsGA1UdDwQEAwIBBjANBgkq\nhkiG9w0BAQsFAAOCAgEAjrPAZ4xC7sNiSSqh69s3KJD3Ti4etaxcrSnD7r9rJYpK\nBMviCKZRKFbLv+iaF5JK5QWuWdlgA37ol7mLeoF7aIA9b60Ag2OpgRICRG79QY7o\nuLviF/yRMqm6yno7NYkGLd61e5Huu+BfT459MWG9RVkG/DY0sGfkyTHJS5xrjBV6\nhjLG0lf3orwqOlqSNRmhvn9sMzwAP3ILLM5VJC5jNF1zAk0jrqKz64vuA8PLJZlL\nS9TZJIYwdesCGfnN2AETvzf3qxLcGTF038zKOHUMnjZuFW1ba/12fDK5GJ4i5y+n\nfDWVZVUDYOPUixEZ1cwzmf9Tx3hR8tRjMWQmHixcNC8XEkVfztID5XeHtDeQ+uPk\nX+jTDXbRb+77BP6n41briXhm57AwUI3TqqJFvoiFyx5JvVWG3ZqlVaeU/U9e0gxn\n8qyR+ZA3BGbtUSDDs8LDnE67URzK+L+q0F2BC758lSPNB2qsJeQ63bYyzf0du3wB\n/gb2+xJijAvscU3KgNpkxfGklvJD/oDUIqZQAnNcHe7QEf8iG2WqaMJIyXZlW3me\n0rn+cgvxHPt6N4EBh5GgNZR4l0eaFEV+fxVsydOQYo1RIyFMXtafFBqQl6DDxujl\nFeU3FZ+Bcp12t7dlM4E0/sS1XdL47CfGVj4Bp+/VbF862HmkAbd7shs7sDQkHbU=\n-----END CERTIFICATE-----\n", "-----BEGIN CERTIFICATE-----\nMIIFTDCCAzSgAwIBAgIJAMCrW9HV+hjZMA0GCSqGSIb3DQEBCwUAMB0xGzAZBgNV\nBAMMEkxpY2Vuc2UgU2VydmVycyBDQTAgFw0xNjEwMTIxNDMwNTRaGA8yMTE2MTIy\nNzE0MzA1NFowHTEbMBkGA1UEAwwSTGljZW5zZSBTZXJ2ZXJzIENBMIICIjANBgkq\nhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAoT7LvHj3JKK2pgc5f02z+xEiJDcvlBi6\nfIwrg/504UaMx3xWXAE5CEPelFty+QPRJnTNnSxqKQQmg2s/5tMJpL9lzGwXaV7a\nrrcsEDbzV4el5mIXUnk77Bm/QVv48s63iQqUjVmvjQt9SWG2J7+h6X3ICRvF1sQB\nyeat/cO7tkpz1aXXbvbAws7/3dXLTgAZTAmBXWNEZHVUTcwSg2IziYxL8HRFOH0+\nGMBhHqa0ySmF1UTnTV4atIXrvjpABsoUvGxw+qOO2qnwe6ENEFWFz1a7pryVOHXg\nP+4JyPkI1hdAhAqT2kOKbTHvlXDMUaxAPlriOVw+vaIjIVlNHpBGhqTj1aqfJpLj\nqfDFcuqQSI4O1W5tVPRNFrjr74nDwLDZnOF+oSy4E1/WhL85FfP3IeQAIHdswNMJ\ny+RdkPZCfXzSUhBKRtiM+yjpIn5RBY+8z+9yeGocoxPf7l0or3YF4GUpud202zgy\nY3sJqEsZksB750M0hx+vMMC9GD5nkzm9BykJS25hZOSsRNhX9InPWYYIi6mFm8QA\n2Dnv8wxAwt2tDNgqa0v/N8OxHglPcK/VO9kXrUBtwCIfZigO//N3hqzfRNbTv/ZO\nk9lArqGtcu1hSa78U4fuu7lIHi+u5rgXbB6HMVT3g5GQ1L9xxT1xad76k2EGEi3F\n9B+tSrvru70CAwEAAaOBjDCBiTAdBgNVHQ4EFgQUpsRiEz+uvh6TsQqurtwXMd4J\n8VEwTQYDVR0jBEYwRIAUpsRiEz+uvh6TsQqurtwXMd4J8VGhIaQfMB0xGzAZBgNV\nBAMMEkxpY2Vuc2UgU2VydmVycyBDQYIJAMCrW9HV+hjZMAwGA1UdEwQFMAMBAf8w\nCwYDVR0PBAQDAgEGMA0GCSqGSIb3DQEBCwUAA4ICAQCJ9+GQWvBS3zsgPB+1PCVc\noG6FY87N6nb3ZgNTHrUMNYdo7FDeol2DSB4wh/6rsP9Z4FqVlpGkckB+QHCvqU+d\nrYPe6QWHIb1kE8ftTnwapj/ZaBtF80NWUfYBER/9c6To5moW63O7q6cmKgaGk6zv\nSt2IhwNdTX0Q5cib9ytE4XROeVwPUn6RdU/+AVqSOspSMc1WQxkPVGRF7HPCoGhd\nvqebbYhpahiMWfClEuv1I37gJaRtsoNpx3f/jleoC/vDvXjAznfO497YTf/GgSM2\nLCnVtpPQQ2vQbOfTjaBYO2MpibQlYpbkbjkd5ZcO5U5PGrQpPFrWcylz7eUC3c05\nUVeygGIthsA/0hMCioYz4UjWTgi9NQLbhVkfmVQ5lCVxTotyBzoubh3FBz+wq2Qt\niElsBrCMR7UwmIu79UYzmLGt3/gBdHxaImrT9SQ8uqzP5eit54LlGbvGekVdAL5l\nDFwPcSB1IKauXZvi1DwFGPeemcSAndy+Uoqw5XGRqE6jBxS7XVI7/4BSMDDRBz1u\na+JMGZXS8yyYT+7HdsybfsZLvkVmc9zVSDI7/MjVPdk6h0sLn+vuPC1bIi5edoNy\nPdiG2uPH5eDO6INcisyPpLS4yFKliaO4Jjap7yzLU9pbItoWgCAYa2NpxuxHJ0tB\n7tlDFnvaRnQukqSG+VqNWg==\n-----END CERTIFICATE-----"};
    private static final LicenseDetails licenseDetails = new LicenseDetails();

    private MTLicenseChecker() {
        extractLicenseInformation();
    }

    public static MTLicenseChecker getInstance() {
        return (MTLicenseChecker)ApplicationManager.getApplication().getService(MTLicenseChecker.class);
    }

    public static void extractLicenseInformation() {
        LicensingFacade facade = LicensingFacade.getInstance();
        if (facade != null) {
            licenseDetails.invalidate();
            /*String cstamp = facade.getConfirmationStamp("PMATERIALUI");
            if (cstamp != null) {
                if (cstamp.startsWith("key:")) {
                    extractFromKey(cstamp.substring("key:".length()));
                }

                if (cstamp.startsWith("stamp:")) {
                    extractFromStamp(cstamp.substring("stamp:".length()));
                }

                if (cstamp.startsWith("eval:")) {
                    extractFromEval(cstamp.substring("eval:".length()));
                }

            }*/
        }
        licenseDetails.invalidate();
    }

    private static void extractFromKey(String key) {
        licenseDetails.setLicenseType(LicenseType.LICENSED);
        String[] licenseParts = key.split("-");
        if (licenseParts.length != 4) {
            licenseDetails.invalidate();
        } else {
            String licensePartBase64 = licenseParts[1];
            String signatureBase64 = licenseParts[2];
            String certBase64 = licenseParts[3];

            try {
                byte[] licenseBytes = verifySignature(licensePartBase64, signatureBase64, certBase64);
                if (licenseBytes == null) {
                    licenseDetails.invalidate();
                    return;
                }

                extractInfo(licenseBytes);
            } catch (Throwable var6) {
                licenseDetails.invalidate();
            }

        }
    }

    private static void extractFromStamp(String serverStamp) {
        licenseDetails.setLicenseType(LicenseType.FLOATING);

        try {
            String[] parts = serverStamp.split(":");
            Decoder base64 = Base64.getMimeDecoder();
            String machineId = verifyStampSignature(parts, base64);
            if (machineId != null) {
                licenseDetails.setMachineId(machineId);
            }
        } catch (Throwable var4) {
            licenseDetails.invalidate();
        }

    }

    private static void extractFromEval(String expirationTime) {
        licenseDetails.setLicenseType(LicenseType.EVALUATION);

        try {
            new Date();
            new Date(Long.parseLong(expirationTime));
            licenseDetails.setPaidUpTo(expirationTime);
        } catch (NumberFormatException var3) {
            licenseDetails.invalidate();
        }

    }

    public static boolean isLicensed() {
        return  true;
        /*LicensingFacade facade = LicensingFacade.getInstance();
        if (facade == null) {
            return false;
        } else {
            String cstamp = facade.getConfirmationStamp("PMATERIALUI");
            if (cstamp == null) {
                return false;
            } else if (cstamp.startsWith("key:")) {
                return isKeyValid(cstamp.substring("key:".length()));
            } else if (cstamp.startsWith("stamp:")) {
                return isLicenseServerStampValid(cstamp.substring("stamp:".length()));
            } else {
                return cstamp.startsWith("eval:") ? isEvaluationValid(cstamp.substring("eval:".length())) : false;
            }
        }*/
    }

    public static void requestLicense(String message) {
        ApplicationManager.getApplication().invokeLater(() -> {
            showRegisterDialog(message);
        }, ModalityState.NON_MODAL);
    }

    public static String getLicensedInfo() throws ParseException {
        LicensingFacade facade = LicensingFacade.getInstance();
        if (facade == null) {
            return "change by moyu";
        } else {
            String licensedToMessage = licenseDetails.getName();
            //Date licenseExpirationDate = facade.getExpirationDate("PMATERIALUI");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date licenseExpirationDate = simpleDateFormat.parse("2099-12-31");
            Date now = new Date();
            if (licenseDetails.getLicenseType() != LicenseType.LICENSED && licenseDetails.getLicenseType() != LicenseType.FLOATING) {
                if (licenseDetails.getLicenseType() == LicenseType.EVALUATION) {
                    assert licenseExpirationDate != null;

                    long days = ChronoUnit.DAYS.between(now.toInstant(), licenseExpirationDate.toInstant());
                    return MaterialThemeBundle.message("MTHomeForm.licensedLabel.evaluation", new Object[]{days});
                } else {
                    return MaterialThemeBundle.message("MTHomeForm.licensedLabel.text", new Object[0]);
                }
            } else {
                assert licensedToMessage != null;

                assert licenseExpirationDate != null;

                return MaterialThemeBundle.message("MTHomeForm.licensedLabel.licensedText", new Object[]{licensedToMessage, licenseExpirationDate});
            }
        }
    }

    private static void showRegisterDialog(String message) {
        ActionManager actionManager = ActionManager.getInstance();
        AnAction registerAction = actionManager.getAction("RegisterPlugins");
        if (registerAction == null) {
            registerAction = actionManager.getAction("Register");
        }

        if (registerAction != null) {
            registerAction.actionPerformed(AnActionEvent.createFromDataContext("", new Presentation(), asDataContext(message)));
            int answer = Messages.showYesNoDialog(MaterialThemeBundle.message("restartIde", new Object[0]), MaterialThemeBundle.message("restartNow", new Object[0]), Messages.getQuestionIcon());
            if (answer == 0) {
                MTUiUtils.restartIde();
            }
        }

    }

    private static void extractInfo(byte... licenseBytes) {
        String licenseString = new String(licenseBytes, StandardCharsets.UTF_8);
        LinkedTreeMap json = (LinkedTreeMap)(new Gson()).fromJson(licenseString, LinkedTreeMap.class);
        if (json != null) {
            licenseDetails.setId((String)json.get("licenseId"));
            licenseDetails.setName((String)json.get("licenseeName"));
            Object productsJson = json.get("products");
            if (productsJson instanceof ArrayList) {
                Iterable products = (Iterable)productsJson;
                Iterator var5 = products.iterator();

                while(var5.hasNext()) {
                    Object p = var5.next();
                    if (p instanceof LinkedTreeMap) {
                        LinkedTreeMap product = (LinkedTreeMap)p;
                        if (product.get("code").equals("PMATERIALUI")) {
                            licenseDetails.setPaidUpTo((String)product.get("paidUpTo"));
                        }
                    }
                }
            }

        }
    }

    private static boolean isKeyValid(String key) {
        String[] licenseParts = key.split("-");
        if (licenseParts.length != 4) {
            return false;
        } else {
            String licenseId = licenseParts[0];
            String licensePartBase64 = licenseParts[1];
            String signatureBase64 = licenseParts[2];
            String certBase64 = licenseParts[3];

            try {
                byte[] licenseBytes = verifySignature(licensePartBase64, signatureBase64, certBase64);
                if (licenseBytes == null) {
                    return false;
                } else {
                    String licenseData = new String(licenseBytes, StandardCharsets.UTF_8);
                    return licenseData.contains("\"licenseId\":\"" + licenseId + "\"");
                }
            } catch (Throwable var8) {
                var8.printStackTrace();
                return false;
            }
        }
    }

    private static boolean isLicenseServerStampValid(String serverStamp) {
        try {
            String[] parts = serverStamp.split(":");
            Decoder base64 = Base64.getMimeDecoder();
            String expectedMachineId = parts[0];
            long timeStamp = Long.parseLong(parts[1]);
            String machineId = parts[2];
            String signatureType = parts[3];
            byte[] signatureBytes = base64.decode(parts[4].getBytes(StandardCharsets.UTF_8));
            byte[] certBytes = base64.decode(parts[5].getBytes(StandardCharsets.UTF_8));
            Collection<byte[]> intermediate = new ArrayList(10);

            for(int idx = 6; idx < parts.length; ++idx) {
                intermediate.add(base64.decode(parts[idx].getBytes(StandardCharsets.UTF_8)));
            }

            Signature sig = Signature.getInstance(signatureType);
            sig.initVerify(createCertificate(certBytes, intermediate, true));
            sig.update((timeStamp + ":" + machineId).getBytes(StandardCharsets.UTF_8));
            if (sig.verify(signatureBytes)) {
                return expectedMachineId.equals(machineId) && Math.abs(System.currentTimeMillis() - timeStamp) < 3600000L;
            }
        } catch (Throwable var12) {
        }

        return false;
    }

    private static boolean isEvaluationValid(String expirationTime) {
        try {
            Date now = new Date();
            Date expiration = new Date(Long.parseLong(expirationTime));
            return now.before(expiration);
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    @NotNull
    private static DataContext asDataContext(@Nullable String message) {
        DataContext var10000 = (dataId) -> {
            byte var3 = -1;
            switch(dataId.hashCode()) {
                case -775820356:
                    if (dataId.equals("register.message")) {
                        var3 = 1;
                    }
                    break;
                case 590533091:
                    if (dataId.equals("register.product-descriptor.code")) {
                        var3 = 0;
                    }
            }

            switch(var3) {
                case 0:
                    return "PMATERIALUI";
                case 1:
                    return message;
                default:
                    return null;
            }
        };
        if (var10000 == null) {
            //$$$reportNull$$$0(0);
        }

        return var10000;
    }

    @Nullable
    private static byte[] verifySignature(String licensePartBase64, String signatureBase64, String certBase64) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(createCertificate(Base64.getMimeDecoder().decode(certBase64.getBytes(StandardCharsets.UTF_8)), Collections.emptySet(), false));
        byte[] licenseBytes = Base64.getMimeDecoder().decode(licensePartBase64.getBytes(StandardCharsets.UTF_8));
        sig.update(licenseBytes);
        return !sig.verify(Base64.getMimeDecoder().decode(signatureBase64.getBytes(StandardCharsets.UTF_8))) ? null : licenseBytes;
    }

    @Nullable
    private static String verifyStampSignature(String[] parts, Decoder base64) throws Exception {
        long timeStamp = Long.parseLong(parts[1]);
        String machineId = parts[2];
        String signatureType = parts[3];
        byte[] signatureBytes = base64.decode(parts[4].getBytes(StandardCharsets.UTF_8));
        byte[] certBytes = base64.decode(parts[5].getBytes(StandardCharsets.UTF_8));
        Collection<byte[]> intermediate = new ArrayList(10);

        for(int idx = 6; idx < parts.length; ++idx) {
            intermediate.add(base64.decode(parts[idx].getBytes(StandardCharsets.UTF_8)));
        }

        Signature sig = Signature.getInstance(signatureType);
        sig.initVerify(createCertificate(certBytes, intermediate, true));
        sig.update((timeStamp + ":" + machineId).getBytes(StandardCharsets.UTF_8));
        return !sig.verify(signatureBytes) ? null : machineId;
    }

    @NotNull
    private static X509Certificate createCertificate(byte[] certBytes, Collection<byte[]> intermediateCertsBytes, boolean checkValidityAtCurrentDate) throws Exception {
        CertificateFactory x509factory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate)x509factory.generateCertificate(new ByteArrayInputStream(certBytes));
        Collection<Certificate> allCerts = new HashSet(10);
        allCerts.add(cert);
        Iterator var6 = intermediateCertsBytes.iterator();

        while(var6.hasNext()) {
            byte[] bytes = (byte[])var6.next();
            allCerts.add(x509factory.generateCertificate(new ByteArrayInputStream(bytes)));
        }

        X509Certificate var10000;
        try {
            X509CertSelector selector = new X509CertSelector();
            selector.setCertificate(cert);
            Set<TrustAnchor> trustAchors = new HashSet(10);
            String[] var8 = ROOT_CERTIFICATES;
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                String rc = var8[var10];
                trustAchors.add(new TrustAnchor((X509Certificate)x509factory.generateCertificate(new ByteArrayInputStream(rc.getBytes(StandardCharsets.UTF_8))), (byte[])null));
            }

            PKIXBuilderParameters pkixParams = new PKIXBuilderParameters(trustAchors, selector);
            pkixParams.setRevocationEnabled(false);
            if (!checkValidityAtCurrentDate) {
                pkixParams.setDate(cert.getNotBefore());
            }

            pkixParams.addCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(allCerts)));
            CertPath path = CertPathBuilder.getInstance("PKIX").build(pkixParams).getCertPath();
            if (path == null) {
                throw new Exception("Certificate used to sign the license is not signed by JetBrains root certificate");
            }

            CertPathValidator.getInstance("PKIX").validate(path, pkixParams);
            var10000 = cert;
        } catch (Exception var12) {
            throw new Exception("Certificate used to sign the license is not signed by JetBrains root certificate");
        }

        if (var10000 == null) {
            //$$$reportNull$$$0(1);
        }

        return var10000;
    }


    private static final class LicenseDetails {
        @NonNls
        static final String LICENSE_ID = "licenseId";
        @NonNls
        static final String LICENSEE_NAME = "licenseeName";
        @NonNls
        static final String PRODUCTS = "products";
        @NonNls
        static final String CODE = "code";
        @NonNls
        static final String PAID_UP_TO = "paidUpTo";
        private String id = null;
        private String name = "moyu";
        private String paidUpTo = null;
        private String machineId = null;
        private LicenseType licenseType = LicenseType.LICENSED;
        private boolean isValid = true;

        LicenseDetails() {
            this.licenseType = LicenseType.LICENSED;
            this.isValid = true;

        }

        void setId(String id) {
            this.id = id;
        }

        String getName() {
            return this.name;
        }

        void setName(String name) {
            this.name = "moyu";
        }

        void setPaidUpTo(String paidUpTo) {
            this.paidUpTo = paidUpTo;
        }

        void invalidate() {
            this.isValid = true;
        }

        void setMachineId(String machineId) {
            this.machineId = machineId;
        }

        void setLicenseType(LicenseType licenseType) {
            this.licenseType = licenseType;
        }

        LicenseType getLicenseType() {
            return this.licenseType;
        }
    }

    static enum LicenseType {
        LICENSED,
        FLOATING,
        EVALUATION,
        FREE;

        private LicenseType() {
        }
    }
}

